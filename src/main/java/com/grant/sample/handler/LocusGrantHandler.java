package com.grant.sample.handler;

import java.util.ArrayList;
import java.util.List;

import com.grant.sample.utils.LocusGrantConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.model.RequestParameter;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.oauth2.token.handlers.grant.AbstractAuthorizationGrantHandler;

public class LocusGrantHandler extends AbstractAuthorizationGrantHandler {

    private static final Log log = LogFactory.getLog(LocusGrantHandler.class);

    @Override
    public boolean validateGrant(OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        log.info("Locus Grant Handler : Starting to validate grant");

        boolean isGrantValid = false;

        RequestParameter[] reqParams = tokReqMsgCtx.getOauth2AccessTokenReqDTO().getRequestParameters();

        String nameValue = null;
        String locationValue = null;
        String keyValue = null;

        for (RequestParameter param : reqParams) {
            switch (param.getKey()) {
            case LocusGrantConstants.LOCUS_NAME_GRANT_PARAM:
                nameValue = param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : null;
                break;
            case LocusGrantConstants.LOCUS_LOCATION_GRANT_PARAM:
                locationValue = param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : null;
                break;
            case LocusGrantConstants.LOCUS_KEY_GRANT_PARAM:
                keyValue = param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : null;
                break;
            }
        }

        if (nameValue != null && locationValue != null && keyValue != null) {
            isGrantValid = (isValidName(nameValue) && isValidLocation(locationValue) && isValidKey(keyValue));
            
            if (isGrantValid) {
                AuthenticatedUser locusUser = new AuthenticatedUser();
                locusUser.setUserName(keyValue);
                tokReqMsgCtx.setAuthorizedUser(locusUser);
                tokReqMsgCtx.setScope(tokReqMsgCtx.getOauth2AccessTokenReqDTO().getScope());
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Locus Grant Handler : isGrantValid = " + isGrantValid);
        }

        return isGrantValid;
    }

    private boolean isValidName(String name) {
        // mocking database call with a sample list
        List<String> validNames = new ArrayList<>();
        validNames.add("locus-10-09874");
        validNames.add("locus-10-01219");

        return validNames.contains(name);
    }

    private boolean isValidLocation(String location) {
        // mocking the database with a sample list
        List<String> validLocations = new ArrayList<>();
        validLocations.add("LK-COLOMBO");
        validLocations.add("LK-KANDY");

        return validLocations.contains(location);
    }

    private boolean isValidKey(String key) {
        // mocking database call with a sample list
        List<String> validKeys = new ArrayList<>();
        validKeys.add("0987654321");
        validKeys.add("1234567890");

        return validKeys.contains(key);
    }
}
