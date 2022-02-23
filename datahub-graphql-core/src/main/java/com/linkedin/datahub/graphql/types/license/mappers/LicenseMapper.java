package com.linkedin.datahub.graphql.types.license.mappers;

import com.linkedin.common.Ownership;
import com.linkedin.data.DataMap;
import com.linkedin.datahub.graphql.generated.License;
import com.linkedin.datahub.graphql.generated.EntityType;
import com.linkedin.datahub.graphql.types.common.mappers.OwnershipMapper;
import com.linkedin.datahub.graphql.types.common.mappers.util.MappingHelper;
import com.linkedin.datahub.graphql.types.mappers.ModelMapper;
import com.linkedin.entity.EntityResponse;
import com.linkedin.entity.EnvelopedAspectMap;
import com.linkedin.metadata.key.LicenseKey;
import javax.annotation.Nonnull;

import com.linkedin.metadata.Constants;


public class LicenseMapper implements ModelMapper<EntityResponse, License> {

    public static final LicenseMapper INSTANCE = new LicenseMapper();

    public static License map(@Nonnull final EntityResponse entityResponse) {
        return INSTANCE.apply(entityResponse);
    }

    @Override
    public License apply(@Nonnull final EntityResponse entityResponse) {
        final License result = new License();
        result.setUrn(entityResponse.getUrn().toString());
        result.setType(EntityType.LICENSE);
        EnvelopedAspectMap aspectMap = entityResponse.getAspects();
        MappingHelper<License> mappingHelper = new MappingHelper<>(aspectMap, result);
        mappingHelper.mapToResult(Constants.LICENSE_KEY_ASPECT_NAME, this::mapLicenseKey);
        mappingHelper.mapToResult(Constants.OWNERSHIP_ASPECT_NAME, (license, dataMap) ->
            license.setOwnership(OwnershipMapper.map(new Ownership(dataMap))));

        return mappingHelper.getResult();
    }

    private void mapLicenseKey(@Nonnull License license, @Nonnull DataMap dataMap) {
        final LicenseKey gmsKey = new LicenseKey(dataMap);
        license.setId(gmsKey.getId());
    }
}
