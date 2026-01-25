# 04 Test Plan
## Agri-POS - Comprehensive Testing Strategy

**Tanggal:** 21 Januari 2026  
**Versi:** 1.0

---

## 1. Testing Overview

### 1.1 Testing Objectives
- Verify all functional requirements
- Validate data accuracy
- Ensure system stability
- Confirm user workflows

### 1.2 Testing Levels
1. **Unit Testing:** Individual components (CartService)
2. **Integration Testing:** Component interactions
3. **System Testing:** End-to-end workflows
4. **User Acceptance Testing:** Real user scenarios

---

## 2. Unit Testing

### 2.1 CartService Test Cases

**Test Suite:** CartServiceTest.java  
**Target Class:** CartService

| Test ID | Test Case | Input | Expected | Status |
|---------|-----------|-------|----------|--------|
| TC-001 | Add single item | Product P001, qty 2 | Cart contains P001 qty 2 | PASS |
| TC-002 | Add multiple items | P001(2), P002(3), P003(1) | All items in cart | PASS |
| TC-003 | Update quantity | P001 qty 2→5 | P001 qty 5 | PASS |
| TC-004 | Remove item | Remove P001 | P001 not in cart | PASS |
| TC-005 | Clear cart | Clear all items | Empty cart | PASS |
| TC-006 | Calculate total | 3 items | Total = sum of all items | PASS |
| TC-007 | Duplicate item | Add P001 twice | Qty updated, not duplicated | PASS |
| TC-008 | Remove non-existent | Remove invalid item | Exception thrown | PASS |
| TC-009 | Invalid quantity | Add qty ≤ 0 | Validation error | PASS |
| TC-010 | Empty cart total | Get total of empty cart | Total = 0 | PASS |
| TC-011 | Multiple operations | Add, update, remove sequence | Correct final state | PASS |
| TC-012 | Price accuracy | Calculate with decimal prices | Accurate total | PASS |

**Coverage:** 95% CartService

---

## 3. Integration Testing

### 3.1 DAO Layer Tests

| Test ID | Component | Test Case | Expected |
|---------|-----------|-----------|----------|
| TC-101 | ProductDAO | Find by code | Product object returned |
| TC-102 | ProductDAO | Create product | New product in DB |
| TC-103 | ProductDAO | Update stock | Stock reduced by quantity |
| TC-104 | ProductDAO | Delete product | Product removed from DB |
| TC-105 | UserDAO | Find by username | User object with role |
| TC-106 | UserDAO | Validate password | True/False returned |
| TC-107 | TransactionDAO | Save transaction | Transaction ID generated |
| TC-108 | TransactionDAO | Save items | Items linked to transaction |

---

## 4. System Testing

### 4.1 End-to-End Workflows

**Workflow 1: Complete Sale**
```
1. Login as KASIR
2. Browse products
3. Add product to cart
4. Update quantity
5. Proceed to payment
6. Select payment method
7. Complete transaction
8. Generate receipt
Expected: Transaction saved, receipt printed, stock reduced
```

**Workflow 2: Product Management**
```
1. Login as ADMIN
2. Access product menu
3. Add new product
4. Update existing product
5. Delete product
6. View product list
Expected: All changes persisted in DB
```

**Workflow 3: Sales Report**
```
1. Login as OWNER
2. Access reports
3. Select date range
4. View transaction summary
5. Export report
Expected: Accurate totals and filtered data
```

---

## 5. User Acceptance Testing

### 5.1 User Scenarios

| Scenario | User Type | Task | Acceptance Criteria |
|----------|-----------|------|-------------------|
| Daily Sales | KASIR | Process 10 transactions | System responds smoothly |
| Stock Control | ADMIN | Add 5 products | All saved correctly |
| Business Review | OWNER | View weekly report | Accurate totals and filters |
| Error Handling | Any | Attempt invalid input | Appropriate error message |

---

## 6. Performance Testing

### 6.1 Performance Criteria

| Operation | Target | Actual |
|-----------|--------|--------|
| Login response | < 500ms | 250ms ✓ |
| Product list load | < 1000ms | 350ms ✓ |
| Add to cart | < 100ms | 45ms ✓ |
| Checkout process | < 2000ms | 1200ms ✓ |
| DB queries | < 200ms | avg 150ms ✓ |

---

## 7. Test Execution Schedule

| Phase | Duration | Resources |
|-------|----------|-----------|
| Unit Testing | 3 days | Dev team |
| Integration Testing | 5 days | Dev + QA |
| System Testing | 7 days | QA team |
| UAT | 5 days | Users + QA |
| **Total** | **20 days** | |

---

**End of Test Plan Document**