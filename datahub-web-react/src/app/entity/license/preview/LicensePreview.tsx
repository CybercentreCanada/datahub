import React from 'react';
import { AuditOutlined } from '@ant-design/icons';
import { EntityType, Owner } from '../../../../types.generated';
import DefaultPreviewCard from '../../../preview/DefaultPreviewCard';
import { useEntityRegistry } from '../../../useEntityRegistry';

export const LicensePreview = ({
    urn,
    name,
    description,
    owners,
}: {
    urn: string;
    name: string;
    description?: string | null;
    owners?: Array<Owner> | null;
}): JSX.Element => {
    const entityRegistry = useEntityRegistry();
    return (
        <DefaultPreviewCard
            url={entityRegistry.getEntityUrl(EntityType.License, urn)}
            name={name || ''}
            description={description || ''}
            owners={owners}
            logoComponent={<AuditOutlined style={{ fontSize: '20px' }} />}
            type="License"
        />
    );
};
