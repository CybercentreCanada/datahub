import datahub.emitter.mce_builder as builder
import datahub.metadata.schema_classes as models
from datahub.emitter.mcp import MetadataChangeProposalWrapper
from datahub.emitter.rest_emitter import DatahubRestEmitter

# Create an emitter to DataHub over REST
emitter = DatahubRestEmitter(gms_server="http://localhost:8080", extra_headers={})
model_urn = builder.make_ml_model_urn(
    model_name="my-test-model", platform="science", env="PROD"
)
model_group_urns = [
    builder.make_ml_model_group_urn(
        group_name="my-model-group", platform="science", env="PROD"
    )
]
feature_urns = [
    builder.make_ml_feature_urn(
        feature_name="my-feature", feature_table_name="my-feature-table"
    ),
    builder.make_ml_feature_urn(
        feature_name="my-feature2", feature_table_name="my-feature-table"
    ),
]

metadata_change_proposal = MetadataChangeProposalWrapper(
    entityType="mlModel",
    changeType=models.ChangeTypeClass.UPSERT,
    entityUrn=model_urn,
    aspectName="mlModelProperties",
    aspect=models.MLModelPropertiesClass(
        description="my feature",
        groups=model_group_urns,
        mlFeatures=feature_urns,
        trainingMetrics=[
            models.MLMetricClass(
                name="accuracy", description="accuracy of the model", value="1.0"
            )
        ],
        hyperParams=[
            models.MLHyperParamClass(
                name="hyper_1", description="hyper_1", value="0.102"
            )
        ],
    ),
)

# Emit metadata!
emitter.emit(metadata_change_proposal)
