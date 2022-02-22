package com.linkedin.datahub.graphql.types.license.mappers;

import com.linkedin.common.Deprecation;
import com.linkedin.common.GlobalTags;
import com.linkedin.common.GlossaryTerms;
import com.linkedin.common.InstitutionalMemory;
import com.linkedin.common.Ownership;
import com.linkedin.common.Status;
import com.linkedin.license.EditableLicenseProperties;
import com.linkedin.data.DataMap;
import com.linkedin.datahub.graphql.generated.AccessLevel;
import com.linkedin.datahub.graphql.generated.Chart;
import com.linkedin.datahub.graphql.generated.Container;
import com.linkedin.datahub.graphql.generated.License;
import com.linkedin.datahub.graphql.generated.LicenseProperties;
import com.linkedin.datahub.graphql.generated.DataPlatform;
import com.linkedin.datahub.graphql.generated.Dataset;
import com.linkedin.datahub.graphql.generated.EntityType;
import com.linkedin.datahub.graphql.types.common.mappers.AuditStampMapper;
import com.linkedin.datahub.graphql.types.common.mappers.DeprecationMapper;
import com.linkedin.datahub.graphql.types.common.mappers.InstitutionalMemoryMapper;
import com.linkedin.datahub.graphql.types.common.mappers.OwnershipMapper;
import com.linkedin.datahub.graphql.types.common.mappers.StatusMapper;
import com.linkedin.datahub.graphql.types.common.mappers.StringMapMapper;
import com.linkedin.datahub.graphql.types.common.mappers.util.MappingHelper;
import com.linkedin.datahub.graphql.types.glossary.mappers.GlossaryTermsMapper;
import com.linkedin.datahub.graphql.types.mappers.ModelMapper;
import com.linkedin.datahub.graphql.types.tag.mappers.GlobalTagsMapper;
import com.linkedin.dataset.Datasets;
import com.linkedin.entity.EntityResponse;
import com.linkedin.entity.EnvelopedAspectMap;
import com.linkedin.metadata.key.LicenseKey;
import com.linkedin.metadata.key.DataPlatformKey;
import com.linkedin.metadata.utils.EntityKeyUtils;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import static com.linkedin.metadata.Constants.*;


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
        mappingHelper.mapToResult(LICENSE_KEY_ASPECT_NAME, this::mapLicenseKey);
        mappingHelper.mapToResult(LICENSE_INFO_ASPECT_NAME, this::mapLicenseInfo);
        mappingHelper.mapToResult(OWNERSHIP_ASPECT_NAME, (license, dataMap) ->
            license.setOwnership(OwnershipMapper.map(new Ownership(dataMap))));
        mappingHelper.mapToResult(STATUS_ASPECT_NAME, (license, dataMap) ->
            license.setStatus(StatusMapper.map(new Status(dataMap))));

        return mappingHelper.getResult();
    }

    private void mapLicenseKey(@Nonnull License license, @Nonnull DataMap dataMap) {
        final LicenseKey gmsKey = new LicenseKey(dataMap);
        license.setLicenseId(gmsKey.getLicenseId());
    }

    private void mapLicenseInfo(@Nonnull License license, @Nonnull DataMap dataMap) {
        final com.linkedin.license.LicenseInfo gmsLicenseInfo = new com.linkedin.license.LicenseInfo(dataMap);
        license.setInfo(mapInfo(gmsLicenseInfo));
        license.setProperties(mapLicenseInfoToProperties(gmsLicenseInfo));
    }

    /**
     * Maps GMS {@link com.linkedin.license.LicenseInfo} to deprecated GraphQL {@link LicenseInfo}
     */
    private LicenseInfo mapInfo(final com.linkedin.license.LicenseInfo info) {
        final LicenseInfo result = new LicenseInfo();
        result.setDescription(info.getDescription());
        result.setName(info.getTitle());
        result.setLastRefreshed(info.getLastRefreshed());
        result.setCharts(info.getCharts().stream().map(urn -> {
            final Chart chart = new Chart();
            chart.setUrn(urn.toString());
            return chart;
        }).collect(Collectors.toList()));
        if (info.hasExternalUrl()) {
            result.setExternalUrl(info.getExternalUrl().toString());
        } else if (info.hasLicenseUrl()) {
            // TODO: Migrate to using the External URL field for consistency.
            result.setExternalUrl(info.getLicenseUrl().toString());
        }
        if (info.hasCustomProperties()) {
            result.setCustomProperties(StringMapMapper.map(info.getCustomProperties()));
        }
        if (info.hasAccess()) {
            result.setAccess(AccessLevel.valueOf(info.getAccess().toString()));
        }
        result.setLastModified(AuditStampMapper.map(info.getLastModified().getLastModified()));
        result.setCreated(AuditStampMapper.map(info.getLastModified().getCreated()));
        if (info.getLastModified().hasDeleted()) {
            result.setDeleted(AuditStampMapper.map(info.getLastModified().getDeleted()));
        }
        return result;
    }

    /**
     * Maps GMS {@link com.linkedin.license.LicenseInfo} to new GraphQL {@link LicenseProperties}
     */
    private LicenseProperties mapLicenseInfoToProperties(final com.linkedin.license.LicenseInfo info) {
        final LicenseProperties result = new LicenseProperties();
        result.setDescription(info.getDescription());
        result.setName(info.getName());
        result.setPermissions(info.getNickname());
        result.setNickname(info.getNickname());
        result.setNickname(info.getNickname());

        if (info.hasNickname()) {
            result.setNickname(info.getNickname());
        }

        if (info.hasNote()) {
            result.setNote(info.getNote());
        }
        if (info.hasCustomProperties()) {
            result.setCustomProperties(StringMapMapper.map(info.getCustomProperties()));
        }
        if (info.hasAccess()) {
            result.setAccess(AccessLevel.valueOf(info.getAccess().toString()));
        }
        result.setLastModified(AuditStampMapper.map(info.getLastModified().getLastModified()));
        result.setCreated(AuditStampMapper.map(info.getLastModified().getCreated()));
        if (info.getLastModified().hasDeleted()) {
            result.setDeleted(AuditStampMapper.map(info.getLastModified().getDeleted()));
        }
        return result;
    }

    private void mapDatasets(@Nonnull License license, @Nonnull DataMap dataMap) {
        final Datasets datasets = new Datasets(dataMap);
        // Currently we only take the first domain if it exists.
        if (datasets.getDatasets().size() > 0) {
            license.setDatasets(Dataset.builder()
                .setType(EntityType.DATASET)
                .setUrn(datasets.getDatasets().get(0).toString()).build());
        }
    }
}
