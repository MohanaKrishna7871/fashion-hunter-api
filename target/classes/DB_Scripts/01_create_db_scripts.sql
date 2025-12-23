CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS pgcrypto;

/* =========================================================
   USER LOGIN
   ========================================================= */
CREATE TABLE app_user_login (
    user_id               UUID PRIMARY KEY,
    user_name             VARCHAR(255) NOT NULL ,
    password_hash         VARCHAR(255) NOT NULL,
    email                 VARCHAR(150) UNIQUE,
    status_code           VARCHAR(10) NOT NULL DEFAULT 'Y',
    is_locked             VARCHAR(10) NOT NULL DEFAULT 'N',
    last_login_date_time  TIMESTAMP,
    password_changed_date_time TIMESTAMP,
    created_by            VARCHAR(255) NOT NULL,
    created_date_time     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by            VARCHAR(255),
    updated_date_time     TIMESTAMP
);


/* =========================================================
   USER DETAILS
   ========================================================= */
CREATE TABLE app_user_details (
    user_details_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id           UUID NOT NULL,
    first_name        VARCHAR(255) NOT NULL,
    last_name         VARCHAR(255) NOT NULL,
    phone_number      VARCHAR(20),
    created_by        VARCHAR(255) NOT NULL,
    created_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by        VARCHAR(255),
    updated_date_time TIMESTAMP,

    CONSTRAINT fk_user_details_login
        FOREIGN KEY (user_id)
        REFERENCES app_user_login (user_id)
        ON DELETE CASCADE
);


/* =========================================================
   ROLE MASTER
   ========================================================= */
CREATE TABLE app_role (
    role_id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role_code          VARCHAR(150) NOT NULL UNIQUE,
    role_name          VARCHAR(255) NOT NULL,
    description        VARCHAR(255),
    status_code        VARCHAR(10),
    created_by         VARCHAR(255) NOT NULL,
    created_date_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by         VARCHAR(255),
    updated_date_time  TIMESTAMP
);


/* =========================================================
   PERMISSION MASTER
   ========================================================= */
CREATE TABLE app_permission (
    permission_id      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    permission_code    VARCHAR(150) NOT NULL UNIQUE,
    permission_name    VARCHAR(150) NOT NULL,
    description        VARCHAR(255),
    created_by         VARCHAR(255) NOT NULL,
    created_date_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by         VARCHAR(255),
    updated_date_time  TIMESTAMP
);

/* =========================================================
   ROLE ↔ PERMISSION MAPPING
   ========================================================= */
CREATE TABLE app_role_permission (
    role_id            UUID NOT NULL,
    role_code          VARCHAR(150),
    permission_id      UUID NOT NULL,
    permission_code    VARCHAR(150),
    created_by         VARCHAR(255) NOT NULL,
    created_date_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by         VARCHAR(255),
    updated_date_time  TIMESTAMP,

    CONSTRAINT pk_role_permission PRIMARY KEY (role_id, permission_id),
    
    CONSTRAINT fk_rp_role
        FOREIGN KEY (role_id)
        REFERENCES app_role (role_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_rp_permission
        FOREIGN KEY (permission_id)
        REFERENCES app_permission (permission_id)
        ON DELETE CASCADE
);



/* =========================================================
   USER ↔ ROLE MAPPING
   ========================================================= */
CREATE TABLE app_user_role (
    user_id            UUID NOT NULL,
    user_name           VARCHAR(255) ,
    role_id            UUID NOT NULL,
    role_code          VARCHAR(150),
    created_by         VARCHAR(255) NOT NULL,
    created_date_time  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by         VARCHAR(255),
    updated_date_time  TIMESTAMP,

    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_ur_user
        FOREIGN KEY (user_id)
        REFERENCES app_user_login (user_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ur_role
        FOREIGN KEY (role_id)
        REFERENCES app_role (role_id)
        ON DELETE CASCADE
);

/* =========================================================
   PRODUCT + IMAGE (SINGLE TABLE)
   ========================================================= */
CREATE TABLE app_product (
    product_id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_name        VARCHAR(255) NOT NULL,
    description         TEXT,
    status_code         VARCHAR(10),
    image_data          BYTEA,
    sort_order          INTEGER,
    created_by          VARCHAR(255) NOT NULL,
    created_date_time   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by          VARCHAR(255),
    updated_date_time   TIMESTAMP
);

CREATE TABLE app_site_config (
    config_id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shop_code           VARCHAR(50) NOT NULL,
    config_key          VARCHAR(100) NOT NULL,
        -- Example: SITE_ADDRESS, SITE_PHONE, SITE_EMAIL
    config_value        TEXT,
    status_code         VARCHAR(10),
        -- Example: ACTIVE / INACTIVE
    created_by          VARCHAR(100) NOT NULL,
    created_date_time   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by          VARCHAR(100),
    updated_date_time   TIMESTAMP
);

/* =========================================================
   INDEXES (PERFORMANCE)
   ========================================================= */
CREATE INDEX idx_user_login_username
    ON app_user_login (user_name);

CREATE INDEX idx_user_role_user
    ON app_user_role (user_id);

CREATE INDEX idx_role_permission_role
    ON app_role_permission (role_id);
	
/* =========================================================
   PRODUCT TABLE INDEXES
   ========================================================= */
-- 1. Fast lookup by product_id
-- (Primary key already covers this, but useful for clarity)
CREATE INDEX idx_app_product_product_id
    ON app_product (product_id);


-- 2. Product master row only (image_data IS NULL)
-- Used when fetching product details (without images)
CREATE INDEX idx_app_product_master_row
    ON app_product (product_id)
    WHERE image_data IS NULL;

-- 3. Image rows only (image_data IS NOT NULL)
-- Used when fetching product images
CREATE INDEX idx_app_product_image_rows
    ON app_product (product_id)
    WHERE image_data IS NOT NULL;


-- 4. Status-based filtering (ACTIVE / INACTIVE)
CREATE INDEX idx_app_product_status
    ON app_product (status_code);


-- 5. Active product master rows (VERY FAST for listings)
CREATE INDEX idx_app_product_active_master
    ON app_product (product_id)
    WHERE status_code = 'Y'
      AND image_data IS NULL;

CREATE INDEX idx_app_product_name_sort
ON app_product (product_name, sort_order);


CREATE INDEX idx_site_config_shop_code
ON app_site_config (shop_code);