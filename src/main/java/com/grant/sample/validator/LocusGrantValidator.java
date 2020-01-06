package com.grant.sample.validator;

import javax.servlet.http.HttpServletRequest;

import com.grant.sample.utils.LocusGrantConstants;

import org.apache.oltu.oauth2.common.validators.AbstractValidator;

public class LocusGrantValidator extends AbstractValidator<HttpServletRequest> {

    public LocusGrantValidator() {
        // required parameters of locus grant handler
        requiredParams.add(LocusGrantConstants.LOCUS_NAME_GRANT_PARAM);
        requiredParams.add(LocusGrantConstants.LOCUS_LOCATION_GRANT_PARAM);
        requiredParams.add(LocusGrantConstants.LOCUS_KEY_GRANT_PARAM);
    }
}