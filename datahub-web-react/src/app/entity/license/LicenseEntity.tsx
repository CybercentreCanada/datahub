import { AuditOutlined } from '@ant-design/icons';
import * as React from 'react';
import { useGetLicenseQuery, useUpdateLicenseMutation } from '../../../graphql/license.generated';
import { License, EntityType, PlatformType, SearchResult } from '../../../types.generated';
import { Entity, PreviewType } from '../Entity';
import { EntityProfile } from '../shared/containers/profile/EntityProfile';
import { SidebarOwnerSection } from '../shared/containers/profile/sidebar/Ownership/SidebarOwnerSection';
import { SidebarAboutSection } from '../shared/containers/profile/sidebar/SidebarAboutSection';
import { SidebarTagsSection } from '../shared/containers/profile/sidebar/SidebarTagsSection';
import { DashboardChartsTab } from '../shared/tabs/Entity/DashboardChartsTab';
import { PropertiesTab } from '../shared/tabs/Properties/PropertiesTab';
import { GenericEntityProperties } from '../shared/types';
import { LicensePreview } from './preview/LicensePreview';
import { getDataForEntityType } from '../shared/containers/profile/utils';
import { capitalizeFirstLetter } from '../../shared/textUtil';
import { SidebarDomainSection } from '../shared/containers/profile/sidebar/Domain/SidebarDomainSection';

/**
 * Definition of the DataHub License entity.
 */
export class LicenseEntity implements Entity<License> {
    type: EntityType = EntityType.License;

    icon = (fontSize: number) => {
        return (
            <AuditOutlined
                style={{
                    fontSize,
                    color: '#BFBFBF',
                }}
            />
        );
    };

    isSearchEnabled = () => true;

    isBrowseEnabled = () => true;

    isLineageEnabled = () => false;

    getAutoCompleteFieldName = () => 'name';

    getPathName = () => 'license';

    getEntityName = () => 'License';

    getCollectionName = () => 'Licenses';

    renderProfile = (urn: string) => (
        <EntityProfile
            urn={urn}
            entityType={EntityType.License}
            useEntityQuery={useGetLicenseQuery}
            getOverrideProperties={this.getOverridePropertiesFromEntity}
            tabs={[
/*                {
                    name: 'License Information',
                    component: DashboardChartsTab,
                    display: {
                        visible: (_, _1) => true,
                        enabled: (_, _1) => true,
                    },
                },*/
                {
                    name: 'Properties',
                    component: PropertiesTab,
                },
            ]}
            sidebarSections={[
                {
                    component: SidebarAboutSection,
                },
                {
                    component: SidebarTagsSection,
                    properties: {
                        hasTags: true,
                        hasTerms: true,
                    },
                },
                {
                    component: SidebarOwnerSection,
                },
                {
                    component: SidebarDomainSection,
                },
            ]}
        />
    );

    getOverridePropertiesFromEntity = (license?: License | null): GenericEntityProperties => {
        // TODO: Get rid of this once we have correctly formed platform coming back.
        const name = license?.name;
        const description = license?.description;
        const customProperties = license?.customProperties;
        return {
            name,
            properties: {
                description
            },
            customProperties
        };
    };

    renderSearch = (result: SearchResult) => {
        return this.renderPreview(PreviewType.SEARCH, result.entity as License);
    };

    renderPreview = (_: PreviewType, data: License) => {
        return (
            <LicensePreview
                urn={data?.urn}
                name={this.displayName(data)}
                description={data?.description || ''}
                owners={data?.ownership?.owners}
            />
        );
    };

    displayName = (data: License) => {
        return data.name;
    };

    getGenericEntityProperties = (data: License) => {
        return getDataForEntityType({
            data,
            entityType: this.type,
            getOverrideProperties: this.getOverridePropertiesFromEntity,
        });
    };
}
