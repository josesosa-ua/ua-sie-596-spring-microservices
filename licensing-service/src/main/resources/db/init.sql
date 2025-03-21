CREATE TABLE IF NOT EXISTS organizations
(
    organization_id
    VARCHAR
(
    255
) NOT NULL,
    name VARCHAR
(
    255
),
    contact_name VARCHAR
(
    255
),
    contact_email VARCHAR
(
    255
),
    contact_phone VARCHAR
(
    255
),
    CONSTRAINT organizations_pkey PRIMARY KEY
(
    organization_id
)
    );

CREATE TABLE IF NOT EXISTS licenses
(
    license_id
    VARCHAR
(
    255
) NOT NULL,
    organization_id VARCHAR
(
    255
) NOT NULL,
    description VARCHAR
(
    255
),
    product_name VARCHAR
(
    255
),
    license_type VARCHAR
(
    255
),
    comment VARCHAR
(
    255
),
    CONSTRAINT licenses_pkey PRIMARY KEY
(
    license_id
),
    CONSTRAINT fk_organization FOREIGN KEY
(
    organization_id
) REFERENCES organizations
(
    organization_id
)
    );