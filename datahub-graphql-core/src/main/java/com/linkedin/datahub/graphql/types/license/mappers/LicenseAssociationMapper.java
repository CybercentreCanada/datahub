package com.linkedin.datahub.graphql.types.license.mappers;

import javax.annotation.Nonnull;

import com.linkedin.datahub.graphql.generated.LicenseAssociation;
import com.linkedin.datahub.graphql.generated.License;
import com.linkedin.datahub.graphql.types.mappers.ModelMapper;


public class LicenseAssociationMapper implements ModelMapper<com.linkedin.license.LicenseAssociation, LicenseAssociation> {

    public static final LicenseAssociationMapper INSTANCE = new LicenseAssociationMapper();

    public static LicenseAssociation map(@Nonnull final com.linkedin.license.LicenseAssociation licenseAssociation) {
        return INSTANCE.apply(licenseAssociation);
    }

    @Override
    public LicenseAssociation apply(@Nonnull final com.linkedin.license.LicenseAssociation licenseAssociation) {
        final com.linkedin.datahub.graphql.generated.LicenseAssociation result = new com.linkedin.datahub.graphql.generated.LicenseAssociation();
        final License resultLicense = new License();
        resultLicense.setUrn(licenseAssociation.getUrn().toString());
        result.setLicense(resultLicense);
        return result;
    }
}