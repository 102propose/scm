package com.pnt.sign;

import java.util.List;

import com.pnt.common.util.CoreUtils;
import com.pnt.context.config.Configurator;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

final public class SigninUtils {
    
    public static boolean isPermitted(String tMode) {
        boolean checker = false;
        
        String mode;
        List<String> modes = Configurator.getInstance().getListConfig("signin_mode");
        
        
        for (int index = 0; index < modes.size(); index++) {
            
            mode = CoreUtils.ifNull(modes.get(index));
            
            if ("".equals(mode)) {
                continue;
            }
            
            if (mode.trim().equalsIgnoreCase(tMode)) {
                checker = true;
                break;
            }
            
        }
        
        return checker;
    }
}
