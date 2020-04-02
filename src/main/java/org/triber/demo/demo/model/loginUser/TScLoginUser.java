package org.triber.demo.demo.model.loginUser;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * 
 * @ClassName:  SysUser   
 * @Description:TODO(系统用户表)   
 * @author: mayong
 * @date:  2020-04-02
 *
 */
@Data
@TableName("t_sc_login_user")
public class TScLoginUser extends Model<TScLoginUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId("LOGIN_ID")
    @TableField("LOGIN_ID")
    private String loginId;

    /**
     * 用户名称
     */
    @TableField("LOGIN_NAME")
    private String loginName;

    /**
     * 密码
     */
    @TableField("PASS_WORD")
    private String passWord;

    /**
     * 省份
     */
    @TableField("PROVINCE_NO")
    private String provinceNo;

    /**
     * 地市
     */
    @TableField("AREA_NO")
    private String areaNo;

    /**
     * 区县
     */
    @TableField("CITY_NO")
    private String cityNo;

    /**
     * 部门
     */
    @TableField("DEPARTMENT")
    private String department;

    /**
     * 性别（1男  0女）
     */
    @TableField("GENDER")
    private String gender;

    /**
     * 电话
     */
    @TableField("MOBILE_PHONE")
    private String mobilePhone;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /**
     * 修改人（最后修改人）
     */
    @TableField("MODIF_USER")
    private String modifUser;

    /**
     * 修改时间（最后修改时间）
     */
    @TableField("MODIF_DATE")
    private LocalDateTime modifDate;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 机构编码14位
     */
    @TableField("PBC_CODE")
    private String pbcCode;

    /**
     * 数据查阅省
     */
    @TableField("DATA_PROVINCE_NO")
    private String dataProvinceNo;

    /**
     * 数据查阅地市
     */
    @TableField("DATA_AREA_NO")
    private String dataAreaNo;

    /**
     * 数据查阅县
     */
    @TableField("DATA_CITY_NO")
    private String dataCityNo;

    /**
     * 是否初始化密码
     */
    @TableField("INIT_PWD")
    private String initPwd;

    /**
     * 业务类型（如利率，房地产，存贷款报文）
     */
    @TableField("BUSINESS_DEPT")
    private String businessDept;

    /**
     * 01人行用户  02金融机构用户
     */
    @TableField("USER_TYPE")
    private String userType;

    /**
     * 是否数据代报
     */
    @TableField("INSTEAD_REPORT")
    private String insteadReport;

    @TableField("CLASS_ID")
    private String classId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
