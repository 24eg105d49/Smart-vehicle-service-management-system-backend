-- ==============================================================================
-- PostgreSQL Seed Data Script for Smart Vehicle Service Management System (VSMS)
-- Run this script in your Render PostgreSQL database AFTER Spring Boot has
-- started for the first time and created the schema via Hibernate.
-- ==============================================================================

-- 1. Roles (Table: roles, Columns: roleid, rolename)
INSERT INTO roles (roleid, rolename) VALUES
(1, 'Customer'),
(2, 'Service Center'),
(3, 'Admin')
ON CONFLICT (roleid) DO UPDATE SET rolename = EXCLUDED.rolename;

-- 2. Security Questions (Table: securityquestions, Columns: questionid, questiontext)
INSERT INTO securityquestions (questionid, questiontext) VALUES
(1, 'What was your favorite food as a child?'),
(2, 'In what city were you born?'),
(3, 'What is the name of your favorite pet?'),
(4, 'What was the make of your first car?'),
(5, 'What is your favorite sport?'),
(6, 'Who was your childhood hero?')
ON CONFLICT (questionid) DO UPDATE SET questiontext = EXCLUDED.questiontext;

-- 3. Cities (Table: city, Columns: cityid, cityname)
INSERT INTO city (cityid, cityname) VALUES
(1, 'Pune'),
(2, 'Hyderabad'),
(3, 'Bengaluru'),
(4, 'Mumbai'),
(5, 'Delhi'),
(6, 'Chennai')
ON CONFLICT (cityid) DO UPDATE SET cityname = EXCLUDED.cityname;

-- 4. Brands (Table: brands, Columns: brandid, bname)
INSERT INTO brands (brandid, bname) VALUES
(1, 'Maruti Suzuki'),
(2, 'Hyundai'),
(3, 'Kia'),
(4, 'Honda'),
(5, 'Tata'),
(6, 'Mahindra'),
(7, 'MG Motors'),
(8, 'Toyota'),
(9, 'Ford'),
(10, 'Renault')
ON CONFLICT (brandid) DO UPDATE SET bname = EXCLUDED.bname;

-- 5. Areas (Table: area, Columns: areaid, areaname, pincode, cityid)
INSERT INTO area (areaid, areaname, pincode, cityid) VALUES
(1, 'Shivajinagar', '411005', 1),
(2, 'Kothrud', '411038', 1),
(3, 'Karvenagar', '411052', 1),
(4, 'Baner', '411045', 1),
(5, 'Hadapsar', '411013', 1),
(6, 'Kharadi', '411014', 1),
(7, 'Wagholi', '412207', 1),
(8, 'Sinhgad', '411041', 1),
(9, 'Kondhwa', '411048', 1),
(10, 'Katraj', '411037', 1),
(11, 'Ameerpet', '500016', 2),
(12, 'Gachibowli', '500032', 2),
(13, 'Kukatpally', '500072', 2),
(14, 'Banjara Hills', '500034', 2),
(15, 'Madhapur', '500081', 2),
(16, 'Secunderabad', '500003', 2),
(17, 'Dilsukhnagar', '500060', 2),
(18, 'LB Nagar', '500074', 2),
(19, 'Miyapur', '500049', 2),
(20, 'Mehdipatnam', '500028', 2),
(21, 'Indiranagar', '560038', 3),
(22, 'Koramangala', '560095', 3),
(23, 'Jayanagar', '560011', 3),
(24, 'Whitefield', '560066', 3),
(25, 'Hebbal', '560034', 3),
(26, 'Electronic City', '560100', 3),
(27, 'Andheri', '400053', 4),
(28, 'Bandra', '400050', 4),
(29, 'Dadar', '400014', 4),
(30, 'Colaba', '400005', 4),
(31, 'Powai', '400076', 4),
(32, 'Thane', '400601', 4),
(33, 'Saket', '110017', 5),
(34, 'Karol Bagh', '110005', 5),
(35, 'Dwarka', '110075', 5),
(36, 'Rohini', '110085', 5),
(37, 'Lajpat Nagar', '110024', 5),
(38, 'Connaught Place', '110001', 5),
(39, 'T. Nagar', '600017', 6),
(40, 'Anna Nagar', '600040', 6),
(41, 'Adyar', '600020', 6),
(42, 'Velachery', '600042', 6),
(43, 'Porur', '600116', 6),
(44, 'Tambaram', '600045', 6)
ON CONFLICT (areaid) DO UPDATE SET areaname = EXCLUDED.areaname, pincode = EXCLUDED.pincode, cityid = EXCLUDED.cityid;

-- 6. Facilities (Table: facilities, Columns: facilityid, facilityname)
INSERT INTO facilities (facilityid, facilityname) VALUES
(1, 'Engine Oil Check'),
(2, 'Coolant Check'),
(3, 'Battery Check'),
(4, 'Brake Fluid Check'),
(5, 'Lights Check'),
(6, 'Clutch Fluid Check'),
(7, 'Transmission Fluid Check'),
(8, 'Steering Check'),
(9, 'Wind Screen Washer'),
(10, 'Tyre Treads'),
(11, 'Horn Check'),
(12, 'Wheel Alignment'),
(13, 'Shocks Struts'),
(14, 'Differential Check'),
(15, 'Spark Plug'),
(16, 'Air Bag Check'),
(17, 'Engine Lubrication'),
(18, 'Exhaust Check'),
(19, 'Beering Check'),
(20, 'Transmission Belt Check'),
(21, 'Washing'),
(22, 'Internal Cleaning'),
(23, 'Plastic Parts Waxing')
ON CONFLICT (facilityid) DO UPDATE SET facilityname = EXCLUDED.facilityname;

-- ==============================================================================
-- Reset ID sequences to the maximum existing ID values so subsequent inserts
-- made by user registration (Customer, Service Center, etc.) will not fail
-- with unique constraint violation errors.
--
-- Works with both standard serial sequences and PostgreSQL IDENTITY columns.
-- ==============================================================================
DO $$
DECLARE
    seq_name text;
    max_val bigint;
    tables text[] := ARRAY['roles', 'securityquestions', 'city', 'brands', 'area', 'facilities'];
    id_cols text[] := ARRAY['roleid', 'questionid', 'cityid', 'brandid', 'areaid', 'facilityid'];
    i int;
BEGIN
    FOR i IN 1..array_length(tables, 1) LOOP
        seq_name := pg_get_serial_sequence(tables[i], id_cols[i]);
        IF seq_name IS NOT NULL THEN
            EXECUTE format('SELECT COALESCE(MAX(%I), 1) FROM %I', id_cols[i], tables[i]) INTO max_val;
            PERFORM setval(seq_name, max_val);
        END IF;
    END LOOP;
END $$;
