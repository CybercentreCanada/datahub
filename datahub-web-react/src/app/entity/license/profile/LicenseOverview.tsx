import { Col, List, Row, Typography } from 'antd';
import styled from 'styled-components';
import React from 'react';
import { CheckCircleOutlined, WarningOutlined, InfoCircleOutlined, AudioOutlined } from '@ant-design/icons';
import { useEntityData } from '../../shared/EntityContext';
import { useGetLicenseQuery } from '../../../../graphql/license.generated';
import { StringMapEntry } from '../../../../types.generated';

const Section = styled.div`
    padding: 16px 20px;
    width: 100%;
`;

enum IconStyleType {
    GOOD = 'CheckCircleOutlined',
    WARNING = 'WarningOutlined',
    INFO = 'InfoCircleOutlined',
}

const getIcon = (icon: IconStyleType) => {
    switch (icon) {
        case IconStyleType.GOOD:
            return <CheckCircleOutlined style={{ color: 'green' }} />;
        case IconStyleType.WARNING:
            return <WarningOutlined style={{ color: 'red' }} />;
        case IconStyleType.INFO:
            return <InfoCircleOutlined style={{ color: 'blue' }} />;
        default:
            return <AudioOutlined />;
    }
};

const makeList = (items: any, icon: IconStyleType) => {
    const listItems = items || [];
    return (
        <List
            itemLayout="horizontal"
            bordered={false}
            dataSource={listItems}
            renderItem={(item: StringMapEntry) => (
                <List.Item style={{ borderBottom: 'none' }}>
                    <List.Item.Meta avatar={getIcon(icon)} title={item.key} description={item.value} />
                </List.Item>
            )}
        />
    );
};

export const LicenseOverview = (): JSX.Element => {
    const { urn } = useEntityData();
    const { data } = useGetLicenseQuery({ variables: { urn } });
    const { license } = data || {};
    return (
        <Section>
            <Typography.Title level={3}>{license?.properties?.name}</Typography.Title>
            <Row>
                <Col xs={24}>
                    <Typography.Paragraph>{license?.properties?.description}</Typography.Paragraph>
                </Col>
                <Col md={8} xs={24}>
                    <Typography.Title level={5}>Permissions</Typography.Title>
                    {makeList(license?.properties?.permissions, IconStyleType.GOOD)}
                </Col>
                <Col md={8} xs={24}>
                    <Typography.Title level={5}>Limitations</Typography.Title>
                    {makeList(license?.properties?.limitations, IconStyleType.WARNING)}
                </Col>
                <Col md={8} xs={24}>
                    <Typography.Title level={5}>Conditions</Typography.Title>
                    {makeList(license?.properties?.conditions, IconStyleType.INFO)}
                </Col>
            </Row>
        </Section>
    );
};
