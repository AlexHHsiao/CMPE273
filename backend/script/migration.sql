SET SCHEMA 'wlb';

ALTER TABLE IF EXISTS wlb.user ADD COLUMN IF NOT EXISTS is_active boolean NULL;

ALTER TABLE IF EXISTS wlb.home ADD COLUMN IF NOT EXISTS offer_list varchar NULL;

ALTER TABLE IF EXISTS wlb.home ADD COLUMN IF NOT EXISTS available boolean NOT NULL DEFAULT TRUE;

ALTER TABLE IF EXISTS wlb.home ADD COLUMN IF NOT EXISTS description varchar DEFAULT '';

ALTER TABLE wlb.home ADD COLUMN image_url_list json;

ALTER TABLE IF EXISTS wlb.home ADD COLUMN IF NOT EXISTS open_hour varchar DEFAULT '';