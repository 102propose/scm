package com.pnt.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.pnt.common.util.CoreUtils;
import com.pnt.context.config.Configurator;

public class PasswordValidator {
    private Logger logger = Logger.getLogger(this.getClass());
    
    private String userId;
    private String password;
    private String confirmPassword;
    
    public PasswordValidator(String userId, String password, String confirmPassword) {
        this.userId = userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.logger.debug("userId : " + this.userId);
        this.logger.debug("password : " + this.password);
        this.logger.debug("confirm password : " + this.confirmPassword);
    }
    
    public void validate() throws PasswordValidationException {
        String pwdLengthStr = CoreUtils.ifNull(Configurator.getInstance().getConfig("validation_pwd_length"), "0");
        String smallCntStr = CoreUtils.ifNull(Configurator.getInstance().getConfig("validation_pwd_small_letters_count"), "0");
        String capitalCntStr = CoreUtils.ifNull(Configurator.getInstance().getConfig("validation_pwd_capital_letters_count"), "0");
        String numericCntStr = CoreUtils.ifNull(Configurator.getInstance().getConfig("validation_pwd_numeric_letters_count"), "0");
        String specialCntStr = CoreUtils.ifNull(Configurator.getInstance().getConfig("validation_pwd_special_letters_count"), "0");
        
        int lengthCnt = Integer.valueOf(pwdLengthStr).intValue();
        int smallCnt = Integer.valueOf(smallCntStr).intValue();
        int capitalCnt = Integer.valueOf(capitalCntStr).intValue();
        int numericCnt = Integer.valueOf(numericCntStr).intValue();
        int specialCnt = Integer.valueOf(specialCntStr).intValue();
        
        if ("".equals(this.password)) {
            throw new PasswordValidationException("EMPTY_PWD");
        }
        if ("".equals(this.confirmPassword)) {
            throw new PasswordValidationException("EMPTY_CPWD");
        }
        if (!this.confirmPassword.equals(this.password)) {
            throw new PasswordValidationException("NOT_EQUAL_PWD");
        }
        if (this.password.indexOf(this.userId) > -1) {
            throw new PasswordValidationException("INCLUDE_USER_ID");
        }
        
        checkPwdLength(lengthCnt);
        checkKoreanLetters();
        checkSpaceLetter();
        checkInvalidSpecialChars();
        checkSmallLettersCount(smallCnt);
        checkCapitalLettersCount(capitalCnt);
        checkNumericLettersCount(numericCnt);
        checkSpecialLettersCount(specialCnt);
    }
    
    public void checkPwdLength(int length) throws PasswordValidationException {
        
        if (this.password.length() < length) {
            this.logger.debug("password legnth validation exception : " + this.password.length() + ", dest legnth : " + length);
            throw new PasswordValidationException("PWD_LEGNTH_EXCEPTION");
        }
        
    }
    
    public void checkKoreanLetters() throws PasswordValidationException {
        
        Pattern p = Pattern.compile("[가-힣]");
        Matcher m = p.matcher(this.password);
        
        while(m.find()) {
            this.logger.debug("korean letters validation exception ");
            throw new PasswordValidationException("KOREAN_LETTERS_EXCEPTION");
        }
        
    }
    
    public void checkSpaceLetter() throws PasswordValidationException {
        
    	if (this.password.indexOf(" ") > -1) {
    		this.logger.debug("space letters validation exception ");
            throw new PasswordValidationException("SPACE_LETTERS_EXCEPTION");
    	}
    	
    }
    
    public void checkInvalidSpecialChars() throws PasswordValidationException {
        int checker = 0;
        
        String[] specialCharSet = "~`!@#$%^&*()_-+={[}]|:;\"'<,>.?/\\".split("");
//        String[] specialCharSet = new String[]{
//        		"&","\"","'","<",">","\\"
//        };
        
        String letter;
        String specialChar;
        String[] passwordArray = this.password.split("");
        
        for (int i = 0; i < passwordArray.length; i++) {
            letter = passwordArray[i];
            
            if ("".equals(letter)) {
                continue;
            }
            
            for (int x = 0; x < specialCharSet.length; x++) {
                specialChar = specialCharSet[x];
                
                if ("".equals(specialChar)) {
                    continue;
                }
                
                if (letter.equals(specialChar)) {
                    checker++;
                    break;
                }
            }
        }
        
        if (checker > 0) {
            this.logger.debug("special chars validation checker count : " + checker);
            throw new PasswordValidationException("NOTPERMIT_SPECIAL_LETTERS_EXCEPTION");
        }
    }
    
    public void checkSmallLettersCount(int count) throws PasswordValidationException {
    	int checker = 0;
    	
    	Pattern p = Pattern.compile("[a-z]");
    	Matcher m = p.matcher(this.password);
    	
    	while(m.find()) {
    		checker++;
    	}
    	
    	if (checker < count) {
    		this.logger.debug("small letters validation checker count : " + checker + ", dest count : " + count);
    		throw new PasswordValidationException("SMALL_LETTERS_EXCEPTION");
    	}
    }
    
    public void checkCapitalLettersCount(int count) throws PasswordValidationException {
        int checker = 0;
        
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(this.password);
        
        while(m.find()) {
            checker++;
        }
        
        if (checker < count) {
            this.logger.debug("capital letters validation checker count : " + checker + ", dest count : " + count);
            throw new PasswordValidationException("CAPITAL_LETTERS_EXCEPTION");
        }
    }
    
    public void checkNumericLettersCount(int count) throws PasswordValidationException {
        int checker = 0;
        
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(this.password);
        
        while(m.find()) {
            checker++;
        }
        
        if (checker < count) {
            this.logger.debug("numeric letters validation checker count : " + checker + ", dest count : " + count);
            throw new PasswordValidationException("NUMERIC_LETTERS_EXCEPTION");
        }
    }
    
    public void checkSpecialLettersCount(int count) throws PasswordValidationException {
        int checker = 0;
        
        String[] specialCharSet = new String[]{
                "!","@","#","$","%","^","&","*","(",")","_","+","-","=","{","}","[","]",":","\"",";","'","<",">","?",",",".","/","|","\\"
        };
        
        String letter;
        String specialChar;
        String[] passwordArray = this.password.split("");
        
        for (int i = 0; i < passwordArray.length; i++) {
            letter = passwordArray[i];
            
            if ("".equals(letter)) {
                continue;
            }
            
            for (int x = 0; x < specialCharSet.length; x++) {
                specialChar = specialCharSet[x];
                
                if ("".equals(specialChar)) {
                    continue;
                }
                
                if (letter.equals(specialChar)) {
                    checker++;
                    break;
                }
            }
            
        }
        
        if (checker < count) {
            this.logger.debug("special letters validation checker count : " + checker + ", dest count : " + count);
            throw new PasswordValidationException("SPECIAL_LETTERS_EXCEPTION");
        }
    }
    
}
