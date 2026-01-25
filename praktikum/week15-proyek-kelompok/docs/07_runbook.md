# 07 Runbook
## Agri-POS - Setup, Configuration & Deployment Guide

**Tanggal:** 21 Januari 2026  
**Versi:** 1.0

---

## 1. Prerequisites

### 1.1 System Requirements

| Komponen | Requirement | Versi |
|----------|-------------|-------|
| OS | Windows/Linux/macOS | Any |
| RAM | Minimum | 4 GB |
| Storage | Minimum | 2 GB free |
| Java | JDK | 21 |
| Database | PostgreSQL | 12+ |
| Build Tool | Maven | 3.x |

### 1.2 Software Installation

**Java 21 Installation:**
```bash
# Windows: Download JDK 21 from oracle.com
# Set JAVA_HOME environment variable
# Verify: java -version
```

**Maven Installation:**
```bash
# Windows: Download Maven from maven.apache.org
# Set M2_HOME and PATH
# Verify: mvn -version
```

**PostgreSQL Installation:**
```bash
# Windows: Download installer from postgresql.org
# Linux: sudo apt-get install postgresql
# Start service: sudo service postgresql start
```

---

## 2. Database Setup

### 2.1 Create Database & User

```sql
-- Connect as postgres user
psql -U postgres

-- Create database
CREATE DATABASE agripos_database;

-- Create user (if not exists)
CREATE USER postgres WITH PASSWORD 'JERUKAGUNG';

-- Grant privileges
ALTER ROLE postgres WITH SUPERUSER CREATEDB CREATEROLE;

-- Connect to database
\c agripos_database
```

### 2.2 Create Schema

```bash
# Run schema.sql
psql -U postgres -d agripos_database -f sql/schema.sql

# Verify tables created
\dt
```

**Expected Output:**
```
Schema |      Name      | Type  | Owner
--------+----------------+-------+-------
public | products       | table | postgres
public | users          | table | postgres
public | transactions   | table | postgres
public | transaction_items | table | postgres
(4 rows)
```

### 2.3 Load Seed Data

```bash
# Run seed data
psql -U postgres -d agripos_database -f sql/seed.sql

# Verify users
SELECT * FROM users;

# Expected: 3 users (kasir1, admin, owner)
```

---

## 3. Application Setup

### 3.1 Clone Repository

```bash
cd c:\Users\haida\CODE\oop-202501-240202862\praktikum\week15-proyek-kelompok
```

### 3.2 Project Structure

```
week15-proyek-kelompok/
├── pom.xml                 # Maven config
├── src/
│   ├── main/
│   │   └── java/com/agripos/
│   │       ├── model/      # Data models
│   │       ├── dao/        # Data access layer
│   │       ├── service/    # Business logic
│   │       ├── controller/ # MVC controllers
│   │       └── view/       # JavaFX views
│   └── test/              # Unit tests
├── docs/                  # Documentation
│   ├── 01_srs.md
│   ├── 02_arsitektur.md
│   └── ...
└── sql/                   # Database scripts
    ├── schema.sql
    └── seed.sql
```

### 3.3 Build Configuration

**Verify pom.xml:**
```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <javafx.version>21.0.1</javafx.version>
</properties>
```

---

## 4. Database Connection

### 4.1 Update Credentials

**File:** `src/main/java/com/agripos/dao/DatabaseConnection.java`

```java
private static final String URL = "jdbc:postgresql://localhost:5432/agripos_database";
private static final String USERNAME = "postgres";
private static final String PASSWORD = "JERUKAGUNG";
```

### 4.2 Connection Test

```bash
# Compile
mvn clean compile

# Check for connection errors - if none, connection is OK
```

---

## 5. Build Process

### 5.1 Clean Build

```bash
mvn clean compile
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXs
```

### 5.2 Run Tests

```bash
mvn test
```

**Expected Output:**
```
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### 5.3 Package Application

```bash
mvn package
```

**Output:**
```
target/agripos-1.0.jar  # Executable JAR
```

---

## 6. Running Application

### 6.1 Development Mode (Recommended)

```bash
mvn javafx:run
```

**Expected Output:**
```
[INFO] Launching javafx app...
[INFO] Waiting for javafx launch...

// Login window appears
// Application ready
```

### 6.2 Production Mode

```bash
# Build package
mvn package

# Run JAR
java -jar target/agripos-1.0.jar
```

---

## 7. Troubleshooting

### Issue 1: Database Connection Failed

**Error Message:**
```
FATAL: password authentication failed for user postgres
```

**Solutions:**
1. Check PostgreSQL is running: `pg_isready -h localhost`
2. Verify credentials in DatabaseConnection.java
3. Check PostgreSQL password: `sudo -u postgres psql -l`

```bash
# Reset PostgreSQL password (Windows)
psql -U postgres
ALTER USER postgres PASSWORD 'JERUKAGUNG';
```

### Issue 2: Maven Build Failed

**Error Message:**
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
```

**Solutions:**
1. Verify Java 21 installed: `java -version`
2. Set JAVA_HOME: `echo $JAVA_HOME`
3. Clear Maven cache: `mvn clean`

### Issue 3: JavaFX Not Found

**Error Message:**
```
[ERROR] No module named javafx
```

**Solutions:**
```bash
# Update Maven dependencies
mvn dependency:resolve

# Check pom.xml has javafx-controls
# Should have: org.openjfx:javafx-controls:21.0.1
```

### Issue 4: Port Already in Use

**Error Message:**
```
Address already in use
```

**Solutions:**
```bash
# Find process on port 5432 (PostgreSQL)
netstat -ano | findstr :5432

# Kill process (replace PID)
taskkill /PID <PID> /F

# Or restart PostgreSQL service
```

---

## 8. Deployment Checklist

### Pre-Deployment

- [ ] All tests passing (mvn test)
- [ ] Application builds successfully (mvn build)
- [ ] Database connection verified
- [ ] No compiler warnings
- [ ] Documentation complete

### Deployment Steps

```bash
# 1. Clean build
mvn clean build

# 2. Run tests
mvn test

# 3. Package
mvn package

# 4. Copy JAR to deployment folder
cp target/agripos-1.0.jar /deployment/

# 5. Start application
java -jar agripos-1.0.jar
```

### Post-Deployment

- [ ] Application running
- [ ] Database accessible
- [ ] Login working
- [ ] Sample transactions completed
- [ ] Report generated

---

## 9. Maintenance

### 9.1 Database Backup

```bash
# Backup database
pg_dump -U postgres agripos_database > backup_agripos.sql

# Restore database
psql -U postgres agripos_database < backup_agripos.sql
```

### 9.2 Log Files

```bash
# Application logs (if configured)
tail -f logs/agripos.log

# PostgreSQL logs
tail -f /var/log/postgresql/postgresql.log
```

### 9.3 Performance Monitoring

```sql
-- Check table sizes
SELECT schemaname, tablename, pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename))
FROM pg_tables
WHERE schemaname NOT IN ('pg_catalog', 'information_schema');

-- Check query performance
EXPLAIN ANALYZE SELECT * FROM transactions WHERE transaction_date > NOW() - INTERVAL '1 day';
```

### 9.4 Updates & Patching

**For Java Updates:**
```bash
# Update to Java 22+ (when available)
# Update pom.xml <maven.compiler.source> and <maven.compiler.target>
# Test thoroughly before deploying
```

---

## 10. Disaster Recovery

### Scenario: Database Corrupted

```bash
# 1. Stop application
# 2. Restore from backup
psql -U postgres agripos_database < latest_backup.sql

# 3. Verify data
psql -U postgres -d agripos_database -c "SELECT COUNT(*) FROM transactions;"

# 4. Restart application
mvn javafx:run
```

### Scenario: Application Crashed

```bash
# 1. Check logs for errors
# 2. Verify database connection
# 3. Check disk space
# 4. Restart: mvn javafx:run
```

---

## 11. Support & Contacts

| Role | Contact | Responsibility |
|------|---------|-----------------|
| Admin | haida@example.com | Server & DB |
| Dev Lead | developer@example.com | Code issues |
| Support | support@example.com | User issues |

---

**End of Runbook**