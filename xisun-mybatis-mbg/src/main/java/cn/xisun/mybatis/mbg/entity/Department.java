package cn.xisun.mybatis.mbg.entity;

public class Department {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.id
     *
     * @mbg.generated Tue Jul 26 15:12:28 CST 2022
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.dep_name
     *
     * @mbg.generated Tue Jul 26 15:12:28 CST 2022
     */
    private String depName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.id
     *
     * @return the value of department.id
     *
     * @mbg.generated Tue Jul 26 15:12:28 CST 2022
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.id
     *
     * @param id the value for department.id
     *
     * @mbg.generated Tue Jul 26 15:12:28 CST 2022
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.dep_name
     *
     * @return the value of department.dep_name
     *
     * @mbg.generated Tue Jul 26 15:12:28 CST 2022
     */
    public String getDepName() {
        return depName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.dep_name
     *
     * @param depName the value for department.dep_name
     *
     * @mbg.generated Tue Jul 26 15:12:28 CST 2022
     */
    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }
}