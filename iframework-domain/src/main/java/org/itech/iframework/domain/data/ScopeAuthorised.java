package org.itech.iframework.domain.data;

/**
 * ScopeAuthorised
 *
 * @author liuqiang
 */
public interface ScopeAuthorised extends Authorised {
    String COMPANY_ID = "companyId";
    String DEPARTMENT_ID = "departmentId";
    String EMPLOYEE_ID = "employeeId";

    /**
     * 获取公司标识
     *
     * @return
     */
    String getCompanyId();

    /**
     * 获取部门标识
     *
     * @return
     */
    String getDepartmentId();

    /**
     * 获取员工标识
     *
     * @return
     */
    String getEmployeeId();

    /**
     * 获取公司标识字段
     *
     * @return 公司标识字段
     */
    default String getCompanyIdField() {
        return COMPANY_ID;
    }

    /**
     * 获取部门标识字段
     *
     * @return 部门标识字段
     */
    default String getDepartmentIdField() {
        return DEPARTMENT_ID;
    }

    /**
     * 获取员工标识字段
     *
     * @return 员工标识字段
     */
    default String getEmployeeIdField() {
        return EMPLOYEE_ID;
    }
}
