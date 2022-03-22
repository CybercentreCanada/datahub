package com.linkedin.common.urn;

import com.linkedin.data.template.Custom;
import com.linkedin.data.template.DirectCoercer;
import com.linkedin.data.template.TemplateOutputCastException;
import java.net.URISyntaxException;


public final class LicenseUrn extends Urn {

  public static final String ENTITY_TYPE = "license";

  private final String _id;

  public LicenseUrn(String licenseID) {
    super(ENTITY_TYPE, TupleKey.createWithOneKeyPart(licenseID));
    this._id = licenseID;
  }

  public String getGroupNameEntity() {
    return _id;
  }

  public static LicenseUrn createFromString(String rawUrn) throws URISyntaxException {
    return createFromUrn(Urn.createFromString(rawUrn));
  }

  private static LicenseUrn decodeUrn(String id) throws Exception {
    return new LicenseUrn(id);
  }

  public static LicenseUrn createFromUrn(Urn urn) throws URISyntaxException {
    if (!"li".equals(urn.getNamespace())) {
      throw new URISyntaxException(urn.toString(), "Urn namespace type should be 'li'.");
    } else if (!ENTITY_TYPE.equals(urn.getEntityType())) {
      throw new URISyntaxException(urn.toString(), "Urn entity type should be 'license'.");
    } else {
      try {
        return decodeUrn(urn.getId());
      } catch (Exception var3) {
        throw new URISyntaxException(urn.toString(), "Invalid URN Parameter: '" + var3.getMessage());
      }
    }
  }

  public static LicenseUrn deserialize(String rawUrn) throws URISyntaxException {
    return createFromString(rawUrn);
  }

  static {
    Custom.registerCoercer(new DirectCoercer<LicenseUrn>() {
      public Object coerceInput(LicenseUrn object) throws ClassCastException {
        return object.toString();
      }

      public LicenseUrn coerceOutput(Object object) throws TemplateOutputCastException {
        try {
          return LicenseUrn.createFromString((String) object);
        } catch (URISyntaxException e) {
          throw new TemplateOutputCastException("Invalid URN syntax: " + e.getMessage(), e);
        }
      }
    }, LicenseUrn.class);
  }
}
