package com.linkedin.datahub.graphql.types.license.mappers;

import com.linkedin.common.AuditStamp;
import com.linkedin.common.GlobalTags;
import com.linkedin.common.TagAssociationArray;
import com.linkedin.common.urn.Urn;
import com.linkedin.data.template.SetMode;
import com.linkedin.datahub.graphql.generated.LicenseUpdateInput;
import com.linkedin.datahub.graphql.types.common.mappers.OwnershipUpdateMapper;
import com.linkedin.datahub.graphql.types.common.mappers.util.UpdateMappingHelper;
import com.linkedin.datahub.graphql.types.mappers.InputModelMapper;
import com.linkedin.datahub.graphql.types.tag.mappers.TagAssociationUpdateMapper;
import com.linkedin.mxe.MetadataChangeProposal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import static com.linkedin.metadata.Constants.*;


public class LicenseUpdateInputMapper implements
                                        InputModelMapper<LicenseUpdateInput, Collection<MetadataChangeProposal>, Urn> {
    public static final LicenseUpdateInputMapper INSTANCE = new LicenseUpdateInputMapper();

    public static Collection<MetadataChangeProposal> map(@Nonnull final LicenseUpdateInput licenseUpdateInput,
                                @Nonnull final Urn actor) {
        return INSTANCE.apply(licenseUpdateInput, actor);
    }

    @Override
    public Collection<MetadataChangeProposal> apply(@Nonnull final LicenseUpdateInput licenseUpdateInput,
                           @Nonnull final Urn actor) {

        final Collection<MetadataChangeProposal> proposals = new ArrayList<>(3);
        final UpdateMappingHelper updateMappingHelper = new UpdateMappingHelper(LICENSE_ENTITY_NAME);
        final AuditStamp auditStamp = new AuditStamp();
        auditStamp.setActor(actor, SetMode.IGNORE_NULL);
        auditStamp.setTime(System.currentTimeMillis());

        if (licenseUpdateInput.getOwnership() != null) {
            proposals.add(updateMappingHelper.aspectToProposal(
                OwnershipUpdateMapper.map(licenseUpdateInput.getOwnership(), actor), OWNERSHIP_ASPECT_NAME));
        }

        if (licenseUpdateInput.getTags() != null || licenseUpdateInput.getGlobalTags() != null) {
            final GlobalTags globalTags = new GlobalTags();
            if (licenseUpdateInput.getGlobalTags() != null) {
                globalTags.setTags(
                    new TagAssociationArray(
                        licenseUpdateInput.getGlobalTags().getTags().stream().map(
                            element -> TagAssociationUpdateMapper.map(element)
                        ).collect(Collectors.toList())
                    )
                );
            } else {
                // Tags override global tags
                globalTags.setTags(
                    new TagAssociationArray(
                        licenseUpdateInput.getTags().getTags().stream().map(
                            element -> TagAssociationUpdateMapper.map(element)
                        ).collect(Collectors.toList())
                    )
                );
            }
            proposals.add(updateMappingHelper.aspectToProposal(globalTags, GLOBAL_TAGS_ASPECT_NAME));
        }

        return proposals;
    }

}
