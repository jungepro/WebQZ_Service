package org.jeecg.modules.coderQ.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {

    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
    /**姓名*/
    @Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private String name;
    /**账号*/
    @Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "账号")
    private String account;
    /**密码*/
    @Excel(name = "密码", width = 15)
    @ApiModelProperty(value = "密码")
    private String pwd;
    /**电话*/
    @Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private String phone;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    /**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
    /**修改时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateTime;
}
