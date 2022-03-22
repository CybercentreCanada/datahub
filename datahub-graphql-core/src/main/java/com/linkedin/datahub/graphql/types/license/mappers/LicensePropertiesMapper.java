package com.linkedin.datahub.graphql.types.license.mappers;

import javax.annotation.Nonnull;

import com.linkedin.datahub.graphql.generated.LicenseProperties;
import com.linkedin.datahub.graphql.types.common.mappers.StringMapMapper;
import com.linkedin.datahub.graphql.types.mappers.ModelMapper;

/**
 * Maps Pegasus {@link RecordTemplate} objects to objects conforming to the GQL schema.
 *
 * To be replaced by auto-generated mappers implementations
 */
public class LicensePropertiesMapper implements ModelMapper<com.linkedin.license.LicenseProperties, LicenseProperties> {

    public static final LicensePropertiesMapper INSTANCE = new LicensePropertiesMapper();

    public static LicenseProperties map(@Nonnull final com.linkedin.license.LicenseProperties licenseProperties) {
        return INSTANCE.apply(licenseProperties);
    }

    @Override
    public LicenseProperties apply(@Nonnull final com.linkedin.license.LicenseProperties licenseProperties) {
        com.linkedin.datahub.graphql.generated.LicenseProperties licensePropertiesResult = new com.linkedin.datahub.graphql.generated.LicenseProperties();
        
        licensePropertiesResult.setName(licenseProperties.getName());
        licensePropertiesResult.setDescription(licenseProperties.getDescription());
        licensePropertiesResult.setPermissions(StringMapMapper.map(licenseProperties.getPermissions()));
        licensePropertiesResult.setConditions(StringMapMapper.map(licenseProperties.getConditions()));
        licensePropertiesResult.setLimitations(StringMapMapper.map(licenseProperties.getLimitations()));

        if (licenseProperties.hasNickname()) {
            licensePropertiesResult.setNickname(licenseProperties.getNickname());
        }

        if (licenseProperties.hasNote()) {
            licensePropertiesResult.setNote(licenseProperties.getNote());
        }

        if (licenseProperties.hasCustomProperties()) {
            licensePropertiesResult.setCustomProperties(StringMapMapper.map(licenseProperties.getCustomProperties()));
        }

        if (licenseProperties.hasExternalUrl()) {
            licensePropertiesResult.setExternalUrl(licenseProperties.getExternalUrl().toString());
        }
        return licensePropertiesResult;
    }
}
