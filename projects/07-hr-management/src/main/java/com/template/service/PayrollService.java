package com.template.service;

import com.template.entity.*;
import com.template.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;

@Service
@Transactional
public class PayrollService {

    private static final BigDecimal TAX_RATE  = new BigDecimal("0.25");
    private static final BigDecimal PENSION    = new BigDecimal("0.05");

    private final PayslipRepository payslipRepo;
    private final EmployeeRepository employeeRepo;

    public PayrollService(PayslipRepository payslipRepo, EmployeeRepository employeeRepo) {
        this.payslipRepo  = payslipRepo;
        this.employeeRepo = employeeRepo;
    }

    public Payslip generatePayslip(Long employeeId, YearMonth period) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        BigDecimal gross       = employee.getBaseSalary();
        BigDecimal taxAmount   = gross.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal pensionAmt  = gross.multiply(PENSION).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netPay      = gross.subtract(taxAmount).subtract(pensionAmt);

        Payslip payslip = new Payslip();
        payslip.setEmployee(employee);
        payslip.setPeriod(period);
        payslip.setGrossSalary(gross);
        payslip.setTaxDeduction(taxAmount);
        payslip.setPensionDeduction(pensionAmt);
        payslip.setNetPay(netPay);
        return payslipRepo.save(payslip);
    }
}
