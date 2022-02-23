package com.linkedin.datahub.graphql.types.license.mappers;

import com.linkedin.common.AuditStamp;
import com.linkedin.common.urn.Urn;
import com.linkedin.data.template.SetMode;
import com.linkedin.datahub.graphql.generated.LicenseUpdateInput;
import com.linkedin.datahub.graphql.types.common.mappers.OwnershipUpdateMapper;
import com.linkedin.datahub.graphql.types.common.mappers.util.UpdateMappingHelper;
import com.linkedin.datahub.graphql.types.mappers.InputModelMapper;
import com.linkedin.mxe.MetadataChangeProposal;
import java.util.ArrayList;
import java.util.Collection;
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

        return proposals;
    }

}
