CREATE TABLE difficulties
(
    difficulty_id BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    acronym       VARCHAR(255)          NULL,
    CONSTRAINT pk_difficulties PRIMARY KEY (difficulty_id)
);

CREATE TABLE images
(
    image_id   BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    author     VARCHAR(255)          NULL,
    content    LONGBLOB              NULL,
    image_type VARCHAR(255)          NULL,
    poi_id     BIGINT                NULL,
    CONSTRAINT pk_images PRIMARY KEY (image_id)
);

CREATE TABLE itineraries
(
    itinerary_id       BIGINT AUTO_INCREMENT NOT NULL,
    code               VARCHAR(255)          NOT NULL,
    name               VARCHAR(255)          NOT NULL,
    `description`      LONGTEXT              NOT NULL,
    poi_start_id       BIGINT                NULL,
    poi_end_id         BIGINT                NULL,
    itinerary_time     time                  NOT NULL,
    length             FLOAT                 NOT NULL,
    difficulty_id      BIGINT                NOT NULL,
    itinerary_state_id BIGINT                NOT NULL,
    map                LONGTEXT              NOT NULL,
    image              LONGBLOB              NULL,
    CONSTRAINT pk_itineraries PRIMARY KEY (itinerary_id)
);

CREATE TABLE itineraries_categories
(
    itinerary_category_id BIGINT NOT NULL,
    itinerary_id          BIGINT NOT NULL,
    CONSTRAINT pk_itineraries_categories PRIMARY KEY (itinerary_category_id, itinerary_id)
);

CREATE TABLE itinerary_categories
(
    itinerary_category_id BIGINT AUTO_INCREMENT NOT NULL,
    name                  VARCHAR(255)          NULL,
    icon                  BLOB                  NULL,
    CONSTRAINT pk_itinerary_categories PRIMARY KEY (itinerary_category_id)
);

CREATE TABLE itinerary_points
(
    itinerary_point_id BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255)          NULL,
    latitude           DOUBLE                NOT NULL,
    longitude          DOUBLE                NOT NULL,
    CONSTRAINT pk_itinerary_points PRIMARY KEY (itinerary_point_id)
);

CREATE TABLE itinerary_states
(
    itinerary_state_id BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255)          NULL,
    CONSTRAINT pk_itinerary_states PRIMARY KEY (itinerary_state_id)
);

CREATE TABLE municipalities
(
    municipality_id BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255)          NOT NULL,
    zip_code        VARCHAR(255)          NOT NULL,
    region_id       BIGINT                NOT NULL,
    CONSTRAINT pk_municipalities PRIMARY KEY (municipality_id)
);

CREATE TABLE parameters
(
    parameter_id BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255)          NULL,
    type         VARCHAR(255)          NULL,
    CONSTRAINT pk_parameters PRIMARY KEY (parameter_id)
);

CREATE TABLE parameters_values
(
    parameter_value_id BIGINT AUTO_INCREMENT NOT NULL,
    value              VARCHAR(255)          NULL,
    creation_date      datetime              NULL,
    parameter_id       BIGINT                NOT NULL,
    sensor_id          BIGINT                NOT NULL,
    CONSTRAINT pk_parameters_values PRIMARY KEY (parameter_value_id)
);

CREATE TABLE poi_categories
(
    category_id BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NULL,
    CONSTRAINT pk_poi_categories PRIMARY KEY (category_id)
);

CREATE TABLE point_of_interests
(
    poi_id          BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255)          NOT NULL,
    `description`   LONGTEXT              NULL,
    latitude        DOUBLE                NOT NULL,
    longitude       DOUBLE                NOT NULL,
    altitude        BIGINT                NULL,
    municipality_id BIGINT                NOT NULL,
    image_id        BIGINT                NOT NULL,
    CONSTRAINT pk_point_of_interests PRIMARY KEY (poi_id)
);

CREATE TABLE pois_categories
(
    category_id BIGINT NOT NULL,
    poi_id      BIGINT NOT NULL,
    CONSTRAINT pk_pois_categories PRIMARY KEY (category_id, poi_id)
);

CREATE TABLE regions
(
    region_id BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_regions PRIMARY KEY (region_id)
);

CREATE TABLE roles
(
    role_id       BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_roles PRIMARY KEY (role_id)
);

CREATE TABLE sensors
(
    sensor_id      BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255)          NULL,
    ip_address     VARCHAR(255)          NULL,
    sensor_type_id BIGINT                NOT NULL,
    latitude       BIGINT                NULL,
    longitude      BIGINT                NULL,
    CONSTRAINT pk_sensors PRIMARY KEY (sensor_id)
);

CREATE TABLE sensors_parameters
(
    parameter_id BIGINT NOT NULL,
    sensor_id    BIGINT NOT NULL,
    CONSTRAINT pk_sensors_parameters PRIMARY KEY (parameter_id, sensor_id)
);

CREATE TABLE sensors_parameters_values
(
    parameter_value_id BIGINT NOT NULL,
    sensor_id          BIGINT NOT NULL,
    CONSTRAINT pk_sensors_parameters_values PRIMARY KEY (parameter_value_id, sensor_id)
);

CREATE TABLE sensors_type
(
    sensor_id BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255)          NULL,
    CONSTRAINT pk_sensors_type PRIMARY KEY (sensor_id)
);

CREATE TABLE users
(
    user_id  BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255)          NULL,
    password VARCHAR(255)          NULL,
    name     VARCHAR(255)          NULL,
    email    VARCHAR(255)          NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE users_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE municipalities
    ADD CONSTRAINT uc_municipalities_name UNIQUE (name);

ALTER TABLE point_of_interests
    ADD CONSTRAINT uc_point_of_interests_name UNIQUE (name);

ALTER TABLE regions
    ADD CONSTRAINT uc_regions_name UNIQUE (name);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

ALTER TABLE sensors_parameters_values
    ADD CONSTRAINT uc_sensors_parameters_values_parameter_value UNIQUE (parameter_value_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE images
    ADD CONSTRAINT FK_IMAGES_ON_POI FOREIGN KEY (poi_id) REFERENCES point_of_interests (poi_id);

ALTER TABLE itineraries
    ADD CONSTRAINT FK_ITINERARIES_ON_DIFFICULTY FOREIGN KEY (difficulty_id) REFERENCES difficulties (difficulty_id);

ALTER TABLE itineraries
    ADD CONSTRAINT FK_ITINERARIES_ON_ITINERARY_STATE FOREIGN KEY (itinerary_state_id) REFERENCES itinerary_states (itinerary_state_id);

ALTER TABLE itineraries
    ADD CONSTRAINT FK_ITINERARIES_ON_POI_END FOREIGN KEY (poi_end_id) REFERENCES itinerary_points (itinerary_point_id);

ALTER TABLE itineraries
    ADD CONSTRAINT FK_ITINERARIES_ON_POI_START FOREIGN KEY (poi_start_id) REFERENCES itinerary_points (itinerary_point_id);

ALTER TABLE municipalities
    ADD CONSTRAINT FK_MUNICIPALITIES_ON_REGION FOREIGN KEY (region_id) REFERENCES regions (region_id);

ALTER TABLE parameters_values
    ADD CONSTRAINT FK_PARAMETERS_VALUES_ON_PARAMETER FOREIGN KEY (parameter_id) REFERENCES parameters (parameter_id);

ALTER TABLE parameters_values
    ADD CONSTRAINT FK_PARAMETERS_VALUES_ON_SENSOR FOREIGN KEY (sensor_id) REFERENCES sensors (sensor_id);

ALTER TABLE point_of_interests
    ADD CONSTRAINT FK_POINT_OF_INTERESTS_ON_IMAGE FOREIGN KEY (image_id) REFERENCES images (image_id);

ALTER TABLE point_of_interests
    ADD CONSTRAINT FK_POINT_OF_INTERESTS_ON_MUNICIPALITY FOREIGN KEY (municipality_id) REFERENCES municipalities (municipality_id);

ALTER TABLE sensors
    ADD CONSTRAINT FK_SENSORS_ON_SENSOR_TYPE FOREIGN KEY (sensor_type_id) REFERENCES sensors_type (sensor_id);

ALTER TABLE itineraries_categories
    ADD CONSTRAINT fk_iticat_on_itinerary FOREIGN KEY (itinerary_id) REFERENCES itineraries (itinerary_id);

ALTER TABLE itineraries_categories
    ADD CONSTRAINT fk_iticat_on_itinerary_category FOREIGN KEY (itinerary_category_id) REFERENCES itinerary_categories (itinerary_category_id);

ALTER TABLE pois_categories
    ADD CONSTRAINT fk_poicat_on_p_o_i FOREIGN KEY (poi_id) REFERENCES point_of_interests (poi_id);

ALTER TABLE pois_categories
    ADD CONSTRAINT fk_poicat_on_p_o_i_category FOREIGN KEY (category_id) REFERENCES poi_categories (category_id);

ALTER TABLE sensors_parameters
    ADD CONSTRAINT fk_senpar_on_parameter FOREIGN KEY (parameter_id) REFERENCES parameters (parameter_id);

ALTER TABLE sensors_parameters
    ADD CONSTRAINT fk_senpar_on_sensor FOREIGN KEY (sensor_id) REFERENCES sensors (sensor_id);

ALTER TABLE sensors_parameters_values
    ADD CONSTRAINT fk_senparval_on_parameter_value FOREIGN KEY (parameter_value_id) REFERENCES parameters_values (parameter_value_id);

ALTER TABLE sensors_parameters_values
    ADD CONSTRAINT fk_senparval_on_sensor FOREIGN KEY (sensor_id) REFERENCES sensors (sensor_id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (role_id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE difficulties ENGINE = InnoDB;
ALTER TABLE regions ENGINE = InnoDB;
ALTER TABLE municipalities ENGINE = InnoDB;
ALTER TABLE images ENGINE = InnoDB;
ALTER TABLE itineraries ENGINE = InnoDB;
ALTER TABLE itinerary_categories ENGINE = InnoDB;
ALTER TABLE itinerary_states ENGINE = InnoDB;
ALTER TABLE itineraries_categories ENGINE = InnoDB;
ALTER TABLE itinerary_points ENGINE = InnoDB;
ALTER TABLE `parameters` ENGINE = InnoDB;
ALTER TABLE parameters_values ENGINE = InnoDB;
ALTER TABLE point_of_interests ENGINE = InnoDB;
ALTER TABLE pois_categories ENGINE = InnoDB;
ALTER TABLE poi_categories ENGINE = InnoDB;
ALTER TABLE roles ENGINE = InnoDB;
ALTER TABLE sensors ENGINE = InnoDB;
ALTER TABLE sensors_type ENGINE = InnoDB;
ALTER TABLE sensors_parameters ENGINE = InnoDB;
ALTER TABLE sensors_parameters_values ENGINE = InnoDB;
ALTER TABLE users ENGINE = InnoDB;
ALTER TABLE users_roles ENGINE = InnoDB;


INSERT INTO `roles` (`name`, `description`) VALUES
('administrator', 'Administrator');
INSERT INTO `users` (`username`, `password`, `name`, `email`) VALUES
('admin', '$2a$10$WrVDhTbtzUQvvh0Lw7KxzOdYrAoYgxf.AN4/vYYrQxQbXOBZDgynW', 'Administrator', 'admin@admin.it');
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1);
INSERT INTO `itinerary_categories` (`name`, `icon`) VALUES
('A Piedi', 0x89504e470d0a1a0a0000000d494844520000001e0000001e08060000003b30aea200000006624b474400ff00ff00ffa0bda793000000097048597300002e2300002e230178a53f760000000774494d4507e00b090f08157e10e667000005344944415448c7b5975b4c146714c77f333bcb5e8175715d595685258a087211ab48b1a235866ab5b50dc618d234a66d2c36f1a509491335da271f7dd0f8d09726c6c49aa8adbd57ad528277900a25802cb8d505e4e22e2bee95993e6cb045d7ddb14de7f19b6fce6fcee5fb7fe708a87c8e9d3ad4e21df194fb02e3ba48342c2828000808a469758a25dd1a76d8e6b7edaa6bac52634f48f6f2ccc5e3c59deeb6e6fb43fd99d3a0d406059c73f3fc45aef2eaadebea3b5e1a7cf8c481ee2e77fb22b5c0443f50e82aedd9b3637f816af0bea3bb83c3e35e7d2ae3069d915cc7424451c433e42630e97f6e8fddea081d6c386248096e3cfc41d41718939e5d1705915fbebac6a8d74f71a58bd2aa025695ac657de5166459e6d2cd1ff8adf52764457e0e6e49cf8a1ddaf3857686bd673d4d0405d0eb8cb83b1ee01f7bcc03f708d9b679ac2a598bddea207bb693caa53558336d0923e30b8c49fb8eee0e26041f3e71a03b5978bb6ef7a2c80a8aac9065cf40d24833bcb36739c830595e9896e171affef08903dd33c0672e1e2fee72b72f4a964f4501847866c6862658bf720bce39b944631142912043a3f719f10d25ad892e77fba233178f170348009d7dadcdc9aa57d248347fdb8e22c7f72cca28a3a2b08a87e383fcde7b03cfa09beb9d4d292b5d41a1b3afb519b08800f7870732937d70fed475c24f2200ccb7e7f34dd36900e658b371e514706ff0aeea6336cd128f9d3ad492eaac8aa2402c3605c05bdb362369feae3f97b380fa4d0d988d19aac00a0ac74e1d6a91bc239ef29462200a6834225331194123208a1a26267d8cf91e92699ec56ccb1c745a1dfe2999db97bb89c5644aab17a237a625b4e71df1948bbec0b82e1578edd6e568240d0081870100461f0df363cb69ce359dc4a837f3c9f6bd7cfadee7ac28ad21164a1e415f605c2745a2612115383615a3b2b6989b17bac8cdcb8dab96de8420085cbb7309dfc41835459bd8585d47cbadcbd46eace17cebd784a3a184f622d1b020aad5e2c2e5b97cb4733b1f7efcfed335ada44356646eb45f65f98a4abc9303d437bc4ba7a795e85434699ec59711fea0cd8f3f32168f422c4a30f4381e76af8f58345e7c2b37143330d88b2c4f252f58014135d8929e459a365e12d64c1b8579a544fc223d6d1e0076ee799b88104210c494379794a6d529e1684815fdcdd5db70dae3393619ccbcbe7233b3744ece7eb986556f2cc59c63201c09a5b493a6d529a225dd1a5603953412f9f30a49d3ea08869f108ac4353f2f6701ab97555252568046d4a88c9c352c3a6cf3dbd46cdeb2660716f32c00be6d3ac9f92bdf11098591242daf6c2841a7d7110c0755811db6f96de2aebac6aa5479d688122b8a5fc3a03771f6d7e35cb87e8ebd7b3fa36c49055131846bf14224b30625c15d9c28bfbbea1aab4400a73dd79f0c5afbea3b18f52600baef7570e74a1f1d57dd74f577623666e07216100c4daaf2769a250214e52fab7e91d72683993515b5005cbaf93db76eb531d83f8aa4d5b0666b05695a1d73b37254a981804051feb2eaa7e0adebea3b0a5da53d89da9d05d9f964982c08824053ebcff4b6f7e3e919c298a1a7bca498c0a41f93219d7453666a117295f64c779e4f0fdc9e1dfb0bec564768a6c2402416e1cfa17e7a3d7f30f2688847238f998ac954d42cc6966323f06402ef8827657eed5647e89f1d67d2664f40c0683031cfee221c0d31e0bd8ba76710ffd824794bb249b7985938af90482c8267d0cd941c7b91f03cd7ecfda7f656cdf3a2f636a1b61d6c386258e22aeb7919394d54484b5c653d89a0ea4698bed6e6fbc3032f37c2d873fd45f9cbfedd08f37f0f6d7f01786d214fb206d8bd0000000049454e44ae426082),
('In Bici', 0x89504e470d0a1a0a0000000d494844520000001e0000001e08060000003b30aea200000006624b474400ff00ff00ffa0bda793000000097048597300002e2300002e230178a53f760000000774494d4507e40a0f072b29625f8843000005be4944415448c7cd976b5094e715c77fcffbee8d5dd745d705c29d11310405cc20849b1575944473915c9bb1d34e328de98c6dd2993a697562d3b421e9a49398891f24136933491b93189c88654c5b311245c5d8643586111c13011776d95d1717f6e2aeefdb0fc00a0a85b464da33f37e39cf79deff799ef33f9747304d5155b536180edcfdc5b913d9075a1b0c2e8f0380046b32d56535a1c28525e7e3f4c6fd42885f4de77f620ab0c46834b2a7b67e7385a3bf6b5a0e26dbd2d9f2d8cb47341aed034208e7b7065655f5cde7df78faf1e9024ee4c0b627b6d70b211e9f3670203474e6e77fd8b0881990577ff1ce97468369f194c01e9fab6bcb8e8d69cca0d46eaaebb6c627a48fd549379e74a64101b6ecd89816080d9d99f0c4aaaabef9e40b3513c6c3a08be3dee58f52b5742d037e2fdbfff21cbdee9e6fedc0cead0db1988b51f63effc6d37d131149abd1f1e89d1b494dcca4f1f06ec291101d17cfa2aaca7f4ab82421845303108d463e988cbd8aa2e0f23ab82dab808bbde71918bc3c6e5d236bc8c958c4d2bc4ae65a6c980cb338dfddcebec3ef12080d8eb375f477118d46f6009512406dfde6cac9bcbca644397efa13743a0389d694496caea1d3ea49b1653067f63c7c7ecf4da031a2d56fae00d0a8aa5afbe40b35131a49924c697e15698959c4e98dacafda40241a41dc900b8aaaa2d3e8180af9397efa130eb4364c7add8efe2e54557d51130c05d64d66248f0067a7e5e2f639693aba07714306aaa84842b0203d8f5525f73067b675ca5807c38175f2da8756bd62ef68d38ca7ba400809214051150a728a5114155992f9dbb1bd38bd8ed8e7f23a088683e4cdbf1d5551f0f85c04c3012efb3d082101ea4dc049d694d9724a81f9774341ff3816af285ec7fd2b7f4875f9fd64dc321f499279e59d67a95cb29afcec223e3f771c650cab531332a92aba93c696dde8b47a1ea9fe31e5852b29ce5b86411787d3e32012bd1ab3eff35cd2c8b9a5a9cf8d05fdd9f7b7919592c3df8f7fc4317b33b76515106f9e8b4eabe7adc6d729cd5f4e71de32dabfb6633298d169f52ccdab645e7c12ee0117352b36d0d5778143279be8b8f8251585ab2858584ce7c5af088603c3852a38885cb42237067c57f903a4d8d279e98fcf70c9f50db224b3baf43eea3e7c9965b7afc1e7f7d0d8b29bfc054bd9b0f627dc91bf1cb3d1425a6216038397c9c958c45b8d3b70797b494fcae21f6d8d9cbdf039d969b964a7e562ef688b85524ab026c7aea064f1f768687e9b6b4a1459d2b0b2f86e5aedcdb47f6de7d0c926ca0b5712be1aa27edf6bb4da9b31c599295fb28ad4c44cf43a0381d020172e9dc3ed73618a33639b9384cfefe5547b2b19b7cc8fe124589391aacb6a42a30aabc5468feb1b002cb3e6303fed56f636bf8d40e0f23a98179f0840281c60dfe17771797b9184845e6720695e2a965973f9e923cff2d8bd4f9199b28025b796027065d087189383d5653521a930a7a47354e1f37b491a2912de2bfdbcb86b338aaa204932d6f8043c03fdb1cda1ab41bee8383112b321fadc3d44a3114e777ec651fb410e9e68e44ce74900cca6d928ca7532162e2c392fc5198cfb4715a7da5bb9abe24124498ee5e8f0460bcb96ace6b3af8e5c070e073870b48163a70f3118f4d3d9751604d83bdaf8eba7efd3f2cf8fe975f760365a58945d449fe77a5389d31bf70b80dfd43da53afabb30e88dfcf2472fd1ef73d274e403dc3e2799c90b585bf12043c1415edffddb093b972c0f97813565eb29cc29e1c3837fe2424f07d6f804d694aec76cb2f0e7a69df4babb49b6a5f3eb8daf0d5f7c2472b565d3ef1fae1cf186f5553fa028af02a3c184f78a9b965307fe6d191c159d564f59c10a56df711f732d3682a121dace7ecac7ad7bf15e190ed38e67de3ba2d5ea2aa76c8b332963dba2042084706e7b62fb2ebe631919fe9c37cd5c3339e44d35f48d9bb98c06d3e2da4d75dd330d5abba9aefbc649f37f36de4a13191b0da6c53bb736ec4ab6a5ff5744dab9b5a17e22d0ffcf27cc77fd68fb177e4574b8687d03110000000049454e44ae426082),
('A Cavallo', 0x89504e470d0a1a0a0000000d494844520000001e0000001e08060000003b30aea200000006624b474400ff00ff00ffa0bda793000000097048597300002e2300002e230178a53f760000000774494d4507e40a0f072e1bd7ff2d86000005ab4944415448c7cd977f6c95e515c73fcff3beb7f74729bde5d2420b2d2ad42ab413a858573515cc48f1d7b4c13fdc58b2c03259b23f20731aebb26426d425b0cc64ba80b35dc8d4cd4dab4eb681462515eb6ab0ab6be7845628f5f6b6b7a5f427f7debef7bdefd91fdebe85e632bbc9164ff2fef19ee7799fef39e739df73ceab98a78848437c3a7657c789b655875b9b7d432311000a4245d456d725d69655f5f8bd81434aa947e6739efa1cb025b69d7cb1a1e987374786fbe66560517e09f5dbf71e334dcf56a554f43f061691671e7b7ad78ef9026632e0c7df7da24929b563dec0b1c4f9ceddfbb6957319e4e70f3edb15f065577c2ef0c8d8505ffd930f147319a5e1fb073e0d050b4a2e091c4b9ceffcd153df2b4fda160579856caebe97c2d072f634fee0b27bae2fbcd3ddfbb695276d8be08245ecbcef61d695555198bf9c2d376da528bfe40b01efdeb7ad5c441a2ff25844963cf6f4aec1c8701f7939211efaf64fc9cdc943441071d0daa03f7a86a77edfc0d8e4c8bcc194d2883873136ea9522aaa016c3bf987c8701f3e6f805b37dc4e3067115a690c6d601a1eb4d28482056cbae10efcde4046109dde6b68138f99957e3c686db87b22c37dd876f2450003c05c3ed134191bc7e7f5b3f9c67b08e5e603e08893b65891e5c96259fe0a2cdb221cedc5715268a51104d3f0100ae673c5b2528a975ec92deb37b3fe9aaf525359cbe0d9302885652510848e93ef971c3dd4f61353441a76eea903602a3641e1e2e500a49c148367c32c0e2ec19be54344f079fdd46dfa16a1dc7c3a4eb461680347046f96976bafbc0e11a1ec8a0a968696e18883e3a4f8fac66ff2c7a3cf336dc58925ce1319ee43441e37e389d89d33a1587dd53a17e4a3531d1cfbdbeb7ce7de07e716166a2a6ba9a9acfdb7f7eb38290c6d52b6a29c8d1beee0b7870fb86bf1e9d89dbae3645be98c62e85c84be815384a3bd3cff97030c8f464939f67f95c58ee3f07e570b007e6f80bc9cc5ee5ac789b655e6e1d666df8c622a36c1af5efe19a313670130824bf0189ecf3c455073ea8d88a4b35721222835bbee31b3082e0c2122ac2cbe86c1917e77ed706bb34fcf7419808415774101a6e293fce64fbf246927d14abb20b37451eefb85fa9950fb3cbe74820a5abb2583a191c86c01c9249695602a3ec9f8d43937cb93b695e6b7ccee4b4e138ef6326d256675b6c5a9fe9318dae0d3c1d3dcb26ef34511330b4245442f0883cb4ba5b97ecdcddc5ab985dc0579a452364a291e6f7a08bf374055450da7fbbb292db996d60fdf066059c10aeeaeb99f802f1bbf37c0c60db7bb915c989d8b6118d8299b825011666d755de2e06bbff0cd05de7ecf2ed6ac5c8f42d1d5d34e69c91a02fe6c66dae427e18f0178efef6fb9df84a3a709e62ce26b37de8d99ce8dd18911f2168608477b3ff3d4f0505b5d97d06bafaeea9e0b5a517a3dab8a57e3f70678e3afaff0d29b0731b476eff95292b0e2b4b41f41044ef777e38883c7f4f0dc9ff7d3d5f3012260a792ac2dabead17e5fe050a643ec549223efbdccbb1fbe85655b6465f9e6559b6babebb092d3fcfad52718191b22db9f43ee823c4ef59f70a9e9f7060e69a554fddcced3d97d9c175e6fa4b47835dfd8f2002b965e0522384eea92a08636f94a692537adbd8d578f3ec7d0e8804b43908b1a8552ea110d50bf7def3b730feaec3ece6b2dbfe395b79f65e06c384d1dcdcead0fe3cbd0281c27456fa4879367fe414bfb116ebbe12e162d5cccd8c408ce050ca8dfbef798db8f4dd3735fa67efb716f2791e13e2663e3a49c14092bce99819e8c9e0bc2c4d4184fbeb00780eaeb3621082ded473833f089ebad697ab6029869f24745a471e79eba1d17572627cd538bae9e765e7af320762a493269651e1011246d5438dacb07ff6ce5f847ef3231350a3033fc45338e3e976bc82b295cc9f8e439c6d3a073479f2fc7b0f7ff1c6f335684802fbb62ffa3cd8d5f64c02bca2f61ffa3cd4d9940bf9cbf30ffeb9fb67f0138dba4d88830dd490000000049454e44ae426082),
('Cani al seguito', 0x89504e470d0a1a0a0000000d494844520000001e0000001e08060000003b30aea20000001974455874536f6674776172650041646f626520496d616765526561647971c9653c000003d74944415478dabc574b4c1351143d33d35a50b1a58a250a8a953f82422551418dc40fea46311231eefc047541e286c4057e624c5c1913352ed48531c6cfc298b83126261a63c485a08208065004527e8542a55f5adf9d64ea74989629412f697873e7bd77de7df7bcfbce70d068b79e5c79d73fd4533c36e130f8fc5e2e8490e8e7d8df3cbd21644a327b97a5ac68aa3d58bf49cb7c5cac974f5fdd5fd3dad5f4b6d7de6d9480669e90435aea2a6781b5b87c7fc59196b881af3db8d0ded6f5295b2ba0da02f2ac6b3bea0e9fcbd10cdc70f3b47bc0d19f8039308b7999e7e2a91b894a3faf74d45f3be68f07745be91ed4549e88fa9ee6a239630253a4fe804f174f44e9162bb694ecc2decdd5c8c928445156295297a445f4199b18d1d1dcaa5b4d39fddad59c1def561ed97312e5c53bc436f1c1e7f38affef3dbf0ec611f03c0f760ae00ff8916f5d17ce392fb19788349b1ccac937e4b0e3ceb3ab7038877068e7719caa3e2bfe8eee3b23be270cc2a2b6b8adad9d1fdfce96bd724b34cc8775790e9d6b2c5a68127fca4512166b9a44e0de811f46e9a5615e024c498b3130d2173770d202232a37554df3d3564b2661f15491e4d152ceea6a1a60cbd35480a01366e6626b675344d484c9531994772a2dd80cb3310565ebb66b02fed6fd05a3e3230806a7c23e6a8f8e0f63786c001f5adee0f1cbbb1163085347b557eeec1bfc89e54b5762757a2e76971d60cf3d1004617ae5e13878bc6eb0928ac696d728ccb4c1965f86ac15f922f08d479731386a8f58d0dfe3e53070b597aa42f2adde6aab8c5910e4160c05c1ea38da7e7c46737b23bafb3ab0c464414ab285b5bfc3e373472da782ad22f7bcdcf9cbde25e6213dd50abd4e1fbb1eb3a88d496664a6e7b11495a324670302c1003a7eb6e2b7db1573acb0be22efbc92f234d035398eb5d9a59a194d24a345b05b09b91945686a7f1fc16629d270c9e4a25c50043c5be3794195ed12570893a74b5c752b5408a5b99ab1dc0743a1283ba31785034fca01ffc9682d81293f08534772855d5d1b671ae49c708847878a3dcff111cc4e6695ae48031fa618f1c80853471a4979a4d4ac7fb8170f5fdc9e461832aacf85d9ebc1c55652e1fc12a6b8f4344b8673a60102bbdee4912a19ad05548e25ce54b0baa45cd3408e8bcfaf122d618581490d92308b38129ca060b98ef9d401943b11ad2f6148ca333c8294010933e979d2e3128924e57edc35c69e7daac06eef6f56b727c37d5d934e787d9e69a24fae383935b1471a692e8f11bbdf0357ea6eeb63aa4cea208f7c2ee4ad125415988c74300933ad4c8d46249a434d536bfb84611a89e44a5c9f30ecc8107b67f509f3af3fdafe08300091a2b05ec30cad510000000049454e44ae426082),
('Elevato Interesse: storia', 0x4749463839611e001e00a50b000000002424242a2a2a3939394242425a5a5a6666666969696e6e6e7575757777776684527b7b7b6a8757738f617e976d7f986ea2b495b7c5adbdcab4becab5c4cfbcc8d3c1cad4c3d3dbcdd5ddcfdbe2d6e3e8dfe4e9e0e5eae2e9eee7ecefe9f7f9f6fcfcfcfdfefdffffff66845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845266845221f904010a003f002c000000001e001e000006fec09f7048fc5c228f46e311b17888d0e830035958af5808462aed54ad120e482402712457c8863bbc581d1ad1684e9f8b340eab854db156e4758176155613526e0b1c828b741c7a501d568a8c948e0b6b4355157300730a9d8100089c738410530b0e8000acac82adae2322795b3f551a74b0a075ba741a0ba71f568023bda1b07422561e6e12bcbdaebabb2368161189cfc8d1c6738e480b20d9adc59dd275200b490bc4e4d2eed3ca4aebe2efc8c90b4be0f4f5b173e84cd872490bc08080b9460b2234db37604e0801dca82db0206c1e275d05e81ce0a66cc1935b026119d0c8edd7a91f1952add27580a4bd590b6ad95ab0c95d0297c84c11d92089523d252b988658e8e93390a53d5126f861c74804a10586b80c4d15a7299e476c7e6cf8b220cc983267d204cdfa0303572c69649225e2c15a92254d9eb00902003b);

INSERT INTO `difficulties` (`name`, `acronym`) VALUES
('Turistico', 'T'), ('Escursionistico', 'E'), ('Per escursionisti esperti', 'EE');

INSERT INTO `itinerary_states` (`name`) VALUES
('Aperto'), ('Chiuso'), ('Verifica');

INSERT INTO `poi_categories` (`name`) VALUES
('Comune'), ('InfoPoint'), ('Area Faunistica'), ('Centro Visita'), ('Museo'), ('Generico');

INSERT INTO `regions` (`name`) VALUES 
('Abruzzo'), ('Molise'), ('Lazio');

INSERT INTO `municipalities` (`name`, `zip_code`, `region_id`) VALUES
('Acciano', '67020', 1),
('Aielli', '67041', 1),
('Alfedena', '67030', 1),
('Anversa degli Abruzzi', '67030', 1),
('Ateleta', '67030', 1),
('Avezzano', '67051', 1),
('Balsorano', '67052', 1),
('Barete', '67010', 1),
('Barisciano', '67021', 1),
('Barrea', '67030', 1),
('Bisegna', '67050', 1),
('Bugnara', '67030', 1),
('Cagnano Amiterno', '67012', 1),
('Calascio', '67020', 1),
('Campo di Giove', '67030', 1),
('Campotosto', '67013', 1),
('Canistro', '67050', 1),
('Cansano', '67030', 1),
('Capestrano', '67022', 1),
('Capistrello', '67053', 1),
('Capitignano', '67014', 1),
('Caporciano', '67020', 1),
('Cappadocia', '67060', 1),
('Carapelle Calvisio', '67020', 1),
('Carsoli', '67061', 1),
('Castel del Monte', '67023', 1),
('Castel di Ieri', '67020', 1),
('Castel di Sangro', '67031', 1),
('Castellafiume', '67050', 1),
('Castelvecchio Calvisio', '67020', 1),
('Castelvecchio Subequo', '67024', 1),
('Celano', '67043', 1),
('Cerchio', '67044', 1),
('Civita d\'Antino', '67050', 1),
('Civitella Alfedena', '67030', 1),
('Civitella Roveto', '67054', 1),
('Cocullo', '67030', 1),
('Collarmele', '67040', 1),
('Collelongo', '67050', 1),
('Collepietro', '67020', 1),
('Corfinio', '67030', 1),
('Fagnano Alto', '67020', 1),
('Fontecchio', '67020', 1),
('Fossa', '67020', 1),
('Gagliano Aterno', '67020', 1),
('Gioia dei Marsi', '67055', 1),
('Goriano Sicoli', '67030', 1),
('Introdacqua', '67030', 1),
('L\'Aquila', '67100', 1),
('Lecce nei Marsi', '67050', 1),
('Luco dei Marsi', '67056', 1),
('Lucoli', '67045', 1),
('Magliano de\' Marsi', '67062', 1),
('Massa d\'Albe', '67050', 1),
('Molina Aterno', '67020', 1),
('Montereale', '67015', 1),
('Morino', '67050', 1),
('Navelli', '67020', 1),
('Ocre', '67040', 1),
('Ofena', '67025', 1),
('Opi', '67030', 1),
('Oricola', '67063', 1),
('Ortona dei Marsi', '67050', 1),
('Ortucchio', '67050', 1),
('Ovindoli', '67046', 1),
('Pacentro', '67030', 1),
('Pereto', '67064', 1),
('Pescasseroli', '67032', 1),
('Pescina', '67057', 1),
('Pescocostanzo', '67033', 1),
('Pettorano sul Gizio', '67034', 1),
('Pizzoli', '67017', 1),
('Poggio Picenze', '67026', 1),
('Prata d\'Ansidonia', '67020', 1),
('Pratola Peligna', '67035', 1),
('Prezza', '67030', 1),
('Raiano', '67027', 1),
('Rivisondoli', '67036', 1),
('Roccacasale', '67030', 1),
('Rocca di Botte', '67066', 1),
('Rocca di Cambio', '67047', 1),
('Rocca di Mezzo', '67048', 1),
('Rocca Pia', '67030', 1),
('Roccaraso', '67037', 1),
('San Benedetto dei Marsi', '67058', 1),
('San Benedetto in Perillis', '67020', 1),
('San Demetrio ne\' Vestini', '67028', 1),
('San Pio delle Camere', '67020', 1),
('Sante Marie', '67067', 1),
('Sant\'Eusanio Forconese', '67020', 1),
('Santo Stefano di Sessanio', '67020', 1),
('San Vincenzo Valle Roveto', '67050', 1),
('Scanno', '67038', 1),
('Scontrone', '67030', 1),
('Scoppito', '67019', 1),
('Scurcola Marsicana', '67068', 1),
('Secinaro', '67029', 1),
('Sulmona', '67039', 1),
('Tagliacozzo', '67069', 1),
('Tione degli Abruzzi', '67020', 1),
('Tornimparte', '67049', 1),
('Trasacco', '67059', 1),
('Villalago', '67030', 1),
('Villa Santa Lucia degli Abruzzi', '67020', 1),
('Villa Sant\'Angelo', '67020', 1),
('Villavallelonga', '67050', 1),
('Villetta Barrea', '67030', 1),
('Vittorito', '67030', 1),
('Alba Adriatica', '64011', 1),
('Ancarano', '64010', 1),
('Arsita', '64031', 1),
('Atri', '64032', 1),
('Basciano', '64030', 1),
('Bellante', '64020', 1),
('Bisenti', '64033', 1),
('Campli', '64012', 1),
('Canzano', '64020', 1),
('Castel Castagna', '64030', 1),
('Castellalto', '64020', 1),
('Castelli', '64041', 1),
('Castiglione Messer Raimondo', '64034', 1),
('Castilenti', '64035', 1),
('Cellino Attanasio', '64036', 1),
('Cermignano', '64037', 1),
('Civitella del Tronto', '64010', 1),
('Colledara', '64042', 1),
('Colonnella', '64010', 1),
('Controguerra', '64010', 1),
('Corropoli', '64013', 1),
('Cortino', '64040', 1),
('Crognaleto', '64043', 1),
('Fano Adriano', '64044', 1),
('Giulianova', '64021', 1),
('Isola del Gran Sasso d\'Italia', '64045', 1),
('Montefino', '64030', 1),
('Montorio al Vomano', '64046', 1),
('Morro d\'Oro', '64020', 1),
('Mosciano Sant\'Angelo', '64023', 1),
('Nereto', '64015', 1),
('Notaresco', '64024', 1),
('Penna Sant\'Andrea', '64039', 1),
('Pietracamela', '64047', 1),
('Pineto', '64025', 1),
('Rocca Santa Maria', '64010', 1),
('Roseto degli Abruzzi', '64026', 1),
('Sant\'Egidio alla Vibrata', '64016', 1),
('Sant\'Omero', '64027', 1),
('Silvi', '64028', 1),
('Teramo', '64100', 1),
('Torano Nuovo', '64010', 1),
('Torricella Sicura', '64010', 1),
('Tortoreto', '64018', 1),
('Tossicia', '64049', 1),
('Valle Castellana', '64010', 1),
('Martinsicuro', '64014', 1),
('Abbateggio', '65020', 1),
('Alanno', '65020', 1),
('Bolognano', '65020', 1),
('Brittoli', '65010', 1),
('Bussi sul Tirino', '65022', 1),
('Cappelle sul Tavo', '65010', 1),
('Caramanico Terme', '65023', 1),
('Carpineto della Nora', '65010', 1),
('Castiglione a Casauria', '65020', 1),
('Catignano', '65011', 1),
('Cepagatti', '65012', 1),
('Città Sant\'Angelo', '65013', 1),
('Civitaquana', '65010', 1),
('Civitella Casanova', '65010', 1),
('Collecorvino', '65010', 1),
('Corvara', '65020', 1),
('Cugnoli', '65020', 1),
('Elice', '65010', 1),
('Farindola', '65010', 1),
('Lettomanoppello', '65020', 1),
('Loreto Aprutino', '65014', 1),
('Manoppello', '65024', 1),
('Montebello di Bertona', '65010', 1),
('Montesilvano', '65015', 1),
('Moscufo', '65010', 1),
('Nocciano', '65010', 1),
('Penne', '65017', 1),
('Pescara', '65121', 1),
('Pescosansonesco', '65020', 1),
('Pianella', '65019', 1),
('Picciano', '65010', 1),
('Pietranico', '65020', 1),
('Popoli Terme', '65026', 1),
('Roccamorice', '65020', 1),
('Rosciano', '65020', 1),
('Salle', '65020', 1),
('Sant\'Eufemia a Maiella', '65020', 1),
('San Valentino in Abruzzo Citeriore', '65020', 1),
('Scafa', '65027', 1),
('Serramonacesca', '65025', 1),
('Spoltore', '65010', 1),
('Tocco da Casauria', '65028', 1),
('Torre de\' Passeri', '65029', 1),
('Turrivalignani', '65020', 1),
('Vicoli', '65010', 1),
('Villa Celiera', '65010', 1),
('Altino', '66040', 1),
('Archi', '66044', 1),
('Ari', '66010', 1),
('Arielli', '66030', 1),
('Atessa', '66041', 1),
('Bomba', '66042', 1),
('Borrello', '66040', 1),
('Bucchianico', '66011', 1),
('Montebello sul Sangro', '66040', 1),
('Canosa Sannita', '66010', 1),
('Carpineto Sinello', '66030', 1),
('Carunchio', '66050', 1),
('Casacanditella', '66010', 1),
('Casalanguida', '66031', 1),
('Casalbordino', '66021', 1),
('Casalincontrada', '66012', 1),
('Casoli', '66043', 1),
('Castel Frentano', '66032', 1),
('Castelguidone', '66040', 1),
('Castiglione Messer Marino', '66033', 1),
('Celenza sul Trigno', '66050', 1),
('Chieti', '66100', 1),
('Civitaluparella', '66040', 1),
('Civitella Messer Raimondo', '66010', 1),
('Colledimacine', '66010', 1),
('Colledimezzo', '66040', 1),
('Crecchio', '66014', 1),
('Cupello', '66051', 1),
('Dogliola', '66050', 1),
('Fara Filiorum Petri', '66010', 1),
('Fara San Martino', '66015', 1),
('Filetto', '66030', 1),
('Fossacesia', '66022', 1),
('Fraine', '66050', 1),
('Francavilla al Mare', '66023', 1),
('Fresagrandinaria', '66050', 1),
('Frisa', '66030', 1),
('Furci', '66050', 1),
('Gamberale', '66040', 1),
('Gessopalena', '66010', 1),
('Gissi', '66052', 1),
('Giuliano Teatino', '66010', 1),
('Guardiagrele', '66016', 1),
('Guilmi', '66050', 1),
('Lama dei Peligni', '66010', 1),
('Lanciano', '66034', 1),
('Lentella', '66050', 1),
('Lettopalena', '66010', 1),
('Liscia', '66050', 1),
('Miglianico', '66010', 1),
('Montazzoli', '66030', 1),
('Monteferrante', '66040', 1),
('Montelapiano', '66040', 1),
('Montenerodomo', '66010', 1),
('Monteodorisio', '66050', 1),
('Mozzagrogna', '66030', 1),
('Orsogna', '66036', 1),
('Ortona', '66026', 1),
('Paglieta', '66020', 1),
('Palena', '66017', 1),
('Palmoli', '66050', 1),
('Palombaro', '66010', 1),
('Pennadomo', '66040', 1),
('Pennapiedimonte', '66010', 1),
('Perano', '66040', 1),
('Pizzoferrato', '66040', 1),
('Poggiofiorito', '66030', 1),
('Pollutri', '66020', 1),
('Pretoro', '66010', 1),
('Quadri', '66040', 1),
('Rapino', '66010', 1),
('Ripa Teatina', '66010', 1),
('Roccamontepiano', '66010', 1),
('Rocca San Giovanni', '66020', 1),
('Roccascalegna', '66040', 1),
('Roccaspinalveti', '66050', 1),
('Roio del Sangro', '66040', 1),
('Rosello', '66040', 1),
('San Buono', '66050', 1),
('San Giovanni Lipioni', '66050', 1),
('San Giovanni Teatino', '66020', 1),
('San Martino sulla Marrucina', '66010', 1),
('San Salvo', '66050', 1),
('Santa Maria Imbaro', '66030', 1),
('Sant\'Eusanio del Sangro', '66037', 1),
('San Vito Chietino', '66038', 1),
('Scerni', '66020', 1),
('Schiavi di Abruzzo', '66045', 1),
('Taranta Peligna', '66018', 1),
('Tollo', '66010', 1),
('Torino di Sangro', '66020', 1),
('Tornareccio', '66046', 1),
('Torrebruna', '66050', 1),
('Torrevecchia Teatina', '66010', 1),
('Torricella Peligna', '66019', 1),
('Treglio', '66030', 1),
('Tufillo', '66050', 1),
('Vacri', '66010', 1),
('Vasto', '66054', 1),
('Villalfonsina', '66020', 1),
('Villamagna', '66010', 1),
('Villa Santa Maria', '66047', 1),
('Pietraferrazzana', '66040', 1),
('Fallo', '66040', 1),
('Acquaviva Collecroce', '86030', 2),
('Baranello', '86011', 2),
('Bojano', '86021', 2),
('Bonefro', '86041', 2),
('Busso', '86010', 2),
('Campobasso', '86100', 2),
('Campochiaro', '86020', 2),
('Campodipietra', '86010', 2),
('Campolieto', '86040', 2),
('Campomarino', '86042', 2),
('Casacalenda', '86043', 2),
('Casalciprano', '86010', 2),
('Castelbottaccio', '86030', 2),
('Castellino del Biferno', '86020', 2),
('Castelmauro', '86031', 2),
('Castropignano', '86010', 2),
('Cercemaggiore', '86012', 2),
('Cercepiccola', '86010', 2),
('Civitacampomarano', '86030', 2),
('Colle d\'Anchise', '86020', 2),
('Colletorto', '86044', 2),
('Duronia', '86020', 2),
('Ferrazzano', '86010', 2),
('Fossalto', '86020', 2),
('Gambatesa', '86013', 2),
('Gildone', '86010', 2),
('Guardialfiera', '86030', 2),
('Guardiaregia', '86014', 2),
('Guglionesi', '86034', 2),
('Jelsi', '86015', 2),
('Larino', '86035', 2),
('Limosano', '86022', 2),
('Lucito', '86030', 2),
('Lupara', '86030', 2),
('Macchia Valfortore', '86040', 2),
('Mafalda', '86030', 2),
('Matrice', '86030', 2),
('Mirabello Sannitico', '86010', 2),
('Molise', '86020', 2),
('Monacilioni', '86040', 2),
('Montagano', '86023', 2),
('Montecilfone', '86032', 2),
('Montefalcone nel Sannio', '86033', 2),
('Montelongo', '86040', 2),
('Montemitro', '86030', 2),
('Montenero di Bisaccia', '86036', 2),
('Montorio nei Frentani', '86040', 2),
('Morrone del Sannio', '86040', 2),
('Oratino', '86010', 2),
('Palata', '86037', 2),
('Petacciato', '86038', 2),
('Petrella Tifernina', '86024', 2),
('Pietracatella', '86040', 2),
('Pietracupa', '86020', 2),
('Portocannone', '86045', 2),
('Provvidenti', '86040', 2),
('Riccia', '86016', 2),
('Ripabottoni', '86040', 2),
('Ripalimosani', '86025', 2),
('Roccavivara', '86020', 2),
('Rotello', '86040', 2),
('Salcito', '86026', 2),
('San Biase', '86020', 2),
('San Felice del Molise', '86030', 2),
('San Giacomo degli Schiavoni', '86030', 2),
('San Giovanni in Galdo', '86010', 2),
('San Giuliano del Sannio', '86010', 2),
('San Giuliano di Puglia', '86040', 2),
('San Martino in Pensilis', '86046', 2),
('San Massimo', '86027', 2),
('San Polo Matese', '86020', 2),
('Santa Croce di Magliano', '86047', 2),
('Sant\'Angelo Limosano', '86020', 2),
('Sant\'Elia a Pianisi', '86048', 2),
('Sepino', '86017', 2),
('Spinete', '86020', 2),
('Tavenna', '86030', 2),
('Termoli', '86039', 2),
('Torella del Sannio', '86028', 2),
('Toro', '86018', 2),
('Trivento', '86029', 2),
('Tufara', '86010', 2),
('Ururi', '86049', 2),
('Vinchiaturo', '86019', 2),
('Acquaviva d\'Isernia', '86080', 2),
('Agnone', '86081', 2),
('Bagnoli del Trigno', '86091', 2),
('Belmonte del Sannio', '86080', 2),
('Cantalupo nel Sannio', '86092', 2),
('Capracotta', '86082', 2),
('Carovilli', '86083', 2),
('Carpinone', '86093', 2),
('Castel del Giudice', '86080', 2),
('Castelpetroso', '86090', 2),
('Castelpizzuto', '86090', 2),
('Castel San Vincenzo', '86071', 2),
('Castelverrino', '86080', 2),
('Cerro al Volturno', '86072', 2),
('Chiauci', '86097', 2),
('Civitanova del Sannio', '86094', 2),
('Colli a Volturno', '86073', 2),
('Conca Casale', '86070', 2),
('Filignano', '86074', 2),
('Forlì del Sannio', '86084', 2),
('Fornelli', '86070', 2),
('Frosolone', '86095', 2),
('Isernia', '86170', 2),
('Longano', '86090', 2),
('Macchia d\'Isernia', '86070', 2),
('Macchiagodena', '86096', 2),
('Miranda', '86080', 2),
('Montaquila', '86070', 2),
('Montenero Val Cocchiara', '86080', 2),
('Monteroduni', '86075', 2),
('Pesche', '86090', 2),
('Pescolanciano', '86097', 2),
('Pescopennataro', '86080', 2),
('Pettoranello del Molise', '86090', 2),
('Pietrabbondante', '86085', 2),
('Pizzone', '86071', 2),
('Poggio Sannita', '86086', 2),
('Pozzilli', '86077', 2),
('Rionero Sannitico', '86087', 2),
('Roccamandolfi', '86092', 2),
('Roccasicura', '86080', 2),
('Rocchetta a Volturno', '86070', 2),
('San Pietro Avellana', '86088', 2),
('Sant\'Agapito', '86070', 2),
('Santa Maria del Molise', '86096', 2),
('Sant\'Angelo del Pesco', '86080', 2),
('Sant\'Elena Sannita', '86095', 2),
('Scapoli', '86070', 2),
('Sessano del Molise', '86097', 2),
('Sesto Campano', '86078', 2),
('Vastogirardi', '86089', 2),
('Venafro', '86079', 2),
('Acquapendente', '01021', 3),
('Arlena di Castro', '01010', 3),
('Bagnoregio', '01022', 3),
('Barbarano Romano', '01010', 3),
('Bassano Romano', '01030', 3),
('Bassano in Teverina', '01030', 3),
('Blera', '01010', 3),
('Bolsena', '01023', 3),
('Bomarzo', '01020', 3),
('Calcata', '01030', 3),
('Canepina', '01030', 3),
('Canino', '01011', 3),
('Capodimonte', '01010', 3),
('Capranica', '01012', 3),
('Caprarola', '01032', 3),
('Carbognano', '01030', 3),
('Castel Sant\'Elia', '01030', 3),
('Castiglione in Teverina', '01024', 3),
('Celleno', '01020', 3),
('Cellere', '01010', 3),
('Civita Castellana', '01033', 3),
('Civitella d\'Agliano', '01020', 3),
('Corchiano', '01030', 3),
('Fabrica di Roma', '01034', 3),
('Faleria', '01030', 3),
('Farnese', '01010', 3),
('Gallese', '01035', 3),
('Gradoli', '01010', 3),
('Graffignano', '01020', 3),
('Grotte di Castro', '01025', 3),
('Ischia di Castro', '01010', 3),
('Latera', '01010', 3),
('Lubriano', '01020', 3),
('Marta', '01010', 3),
('Montalto di Castro', '01014', 3),
('Montefiascone', '01027', 3),
('Monte Romano', '01010', 3),
('Monterosi', '01030', 3),
('Nepi', '01036', 3),
('Onano', '01010', 3),
('Oriolo Romano', '01010', 3),
('Orte', '01028', 3),
('Piansano', '01010', 3),
('Proceno', '01020', 3),
('Ronciglione', '01037', 3),
('Villa San Giovanni in Tuscia', '01010', 3),
('San Lorenzo Nuovo', '01020', 3),
('Soriano nel Cimino', '01038', 3),
('Sutri', '01015', 3),
('Tarquinia', '01016', 3),
('Tessennano', '01010', 3),
('Tuscania', '01017', 3),
('Valentano', '01018', 3),
('Vallerano', '01030', 3),
('Vasanello', '01030', 3),
('Vejano', '01010', 3),
('Vetralla', '01019', 3),
('Vignanello', '01039', 3),
('Viterbo', '01100', 3),
('Vitorchiano', '01030', 3),
('Accumoli', '02011', 3),
('Amatrice', '02012', 3),
('Antrodoco', '02013', 3),
('Ascrea', '02020', 3),
('Belmonte in Sabina', '02020', 3),
('Borbona', '02010', 3),
('Borgorose', '02021', 3),
('Borgo Velino', '02010', 3),
('Cantalice', '02014', 3),
('Cantalupo in Sabina', '02040', 3),
('Casaprota', '02030', 3),
('Casperia', '02041', 3),
('Castel di Tora', '02020', 3),
('Castelnuovo di Farfa', '02031', 3),
('Castel Sant\'Angelo', '02010', 3),
('Cittaducale', '02015', 3),
('Cittareale', '02010', 3),
('Collalto Sabino', '02022', 3),
('Colle di Tora', '02020', 3),
('Collegiove', '02020', 3),
('Collevecchio', '02042', 3),
('Colli sul Velino', '02010', 3),
('Concerviano', '02020', 3),
('Configni', '02040', 3),
('Contigliano', '02043', 3),
('Cottanello', '02040', 3),
('Fara in Sabina', '02032', 3),
('Fiamignano', '02023', 3),
('Forano', '02044', 3),
('Frasso Sabino', '02030', 3),
('Greccio', '02045', 3),
('Labro', '02010', 3),
('Leonessa', '02016', 3),
('Longone Sabino', '02020', 3),
('Magliano Sabina', '02046', 3),
('Marcetelli', '02020', 3),
('Micigliano', '02010', 3),
('Mompeo', '02040', 3),
('Montasola', '02040', 3),
('Montebuono', '02040', 3),
('Monteleone Sabino', '02033', 3),
('Montenero Sabino', '02040', 3),
('Monte San Giovanni in Sabina', '02040', 3),
('Montopoli di Sabina', '02034', 3),
('Morro Reatino', '02010', 3),
('Nespolo', '02020', 3),
('Orvinio', '02035', 3),
('Paganico Sabino', '02020', 3),
('Pescorocchiano', '02024', 3),
('Petrella Salto', '02025', 3),
('Poggio Bustone', '02018', 3),
('Poggio Catino', '02040', 3),
('Poggio Mirteto', '02047', 3),
('Poggio Moiano', '02037', 3),
('Poggio Nativo', '02030', 3),
('Poggio San Lorenzo', '02030', 3),
('Posta', '02019', 3),
('Pozzaglia Sabina', '02030', 3),
('Rieti', '02100', 3),
('Rivodutri', '02010', 3),
('Roccantica', '02040', 3),
('Rocca Sinibalda', '02026', 3),
('Salisano', '02040', 3),
('Scandriglia', '02038', 3),
('Selci', '02040', 3),
('Stimigliano', '02048', 3),
('Tarano', '02040', 3),
('Toffia', '02039', 3),
('Torricella in Sabina', '02030', 3),
('Torri in Sabina', '02049', 3),
('Turania', '02020', 3),
('Vacone', '02040', 3),
('Varco Sabino', '02020', 3),
('Affile', '00021', 3),
('Agosta', '00020', 3),
('Albano Laziale', '00041', 3),
('Allumiere', '00051', 3),
('Anguillara Sabazia', '00061', 3),
('Anticoli Corrado', '00022', 3),
('Anzio', '00042', 3),
('Arcinazzo Romano', '00020', 3),
('Ariccia', '00072', 3),
('Arsoli', '00023', 3),
('Artena', '00031', 3),
('Bellegra', '00030', 3),
('Bracciano', '00062', 3),
('Camerata Nuova', '00020', 3),
('Campagnano di Roma', '00063', 3),
('Canale Monterano', '00060', 3),
('Canterano', '00020', 3),
('Capena', '00060', 3),
('Capranica Prenestina', '00030', 3),
('Carpineto Romano', '00032', 3),
('Casape', '00010', 3),
('Castel Gandolfo', '00073', 3),
('Castel Madama', '00024', 3),
('Castelnuovo di Porto', '00060', 3),
('Castel San Pietro Romano', '00030', 3),
('Cave', '00033', 3),
('Cerreto Laziale', '00020', 3),
('Cervara di Roma', '00020', 3),
('Cerveteri', '00052', 3),
('Ciciliano', '00020', 3),
('Cineto Romano', '00020', 3),
('Civitavecchia', '00053', 3),
('Civitella San Paolo', '00060', 3),
('Colleferro', '00034', 3),
('Colonna', '00030', 3),
('Fiano Romano', '00065', 3),
('Filacciano', '00060', 3),
('Formello', '00060', 3),
('Frascati', '00044', 3),
('Gallicano nel Lazio', '00010', 3),
('Gavignano', '00030', 3),
('Genazzano', '00030', 3),
('Genzano di Roma', '00045', 3),
('Gerano', '00025', 3),
('Gorga', '00030', 3),
('Grottaferrata', '00046', 3),
('Guidonia Montecelio', '00012', 3),
('Jenne', '00020', 3),
('Labico', '00030', 3),
('Lanuvio', '00075', 3),
('Licenza', '00026', 3),
('Magliano Romano', '00060', 3),
('Mandela', '00020', 3),
('Manziana', '00066', 3),
('Marano Equo', '00020', 3),
('Marcellina', '00010', 3),
('Marino', '00047', 3),
('Mazzano Romano', '00060', 3),
('Mentana', '00013', 3),
('Monte Compatri', '00077', 3),
('Monteflavio', '00010', 3),
('Montelanico', '00030', 3),
('Montelibretti', '00010', 3),
('Monte Porzio Catone', '00078', 3),
('Monterotondo', '00015', 3),
('Montorio Romano', '00010', 3),
('Moricone', '00010', 3),
('Morlupo', '00067', 3),
('Nazzano', '00060', 3),
('Nemi', '00074', 3),
('Nerola', '00017', 3),
('Nettuno', '00048', 3),
('Olevano Romano', '00035', 3),
('Palestrina', '00036', 3),
('Palombara Sabina', '00018', 3),
('Percile', '00020', 3),
('Pisoniano', '00020', 3),
('Poli', '00010', 3),
('Pomezia', '00071', 3),
('Ponzano Romano', '00060', 3),
('Riano', '00060', 3),
('Rignano Flaminio', '00068', 3),
('Riofreddo', '00020', 3),
('Rocca Canterano', '00020', 3),
('Rocca di Cave', '00030', 3),
('Rocca di Papa', '00040', 3),
('Roccagiovine', '00020', 3),
('Rocca Priora', '00079', 3),
('Rocca Santo Stefano', '00030', 3),
('Roiate', '00030', 3),
('Roma', '00118', 3),
('Roviano', '00027', 3),
('Sacrofano', '00060', 3),
('Sambuci', '00020', 3),
('San Gregorio da Sassola', '00010', 3),
('San Polo dei Cavalieri', '00010', 3),
('Santa Marinella', '00058', 3),
('Sant\'Angelo Romano', '00010', 3),
('Sant\'Oreste', '00060', 3),
('San Vito Romano', '00030', 3),
('Saracinesco', '00020', 3),
('Segni', '00037', 3),
('Subiaco', '00028', 3),
('Tivoli', '00019', 3),
('Tolfa', '00059', 3),
('Torrita Tiberina', '00060', 3),
('Trevignano Romano', '00069', 3),
('Vallepietra', '00020', 3),
('Vallinfreda', '00020', 3),
('Valmontone', '00038', 3),
('Velletri', '00049', 3),
('Vicovaro', '00029', 3),
('Vivaro Romano', '00020', 3),
('Zagarolo', '00039', 3),
('Lariano', '00076', 3),
('Ladispoli', '00055', 3),
('Ardea', '00040', 3),
('Ciampino', '00043', 3),
('San Cesareo', '00030', 3),
('Fiumicino', '00054', 3),
('Fonte Nuova', '00013', 3),
('Aprilia', '04011', 3),
('Bassiano', '04010', 3),
('Campodimele', '04020', 3),
('Castelforte', '04021', 3),
('Cisterna di Latina', '04012', 3),
('Cori', '04010', 3),
('Fondi', '04022', 3),
('Formia', '04023', 3),
('Gaeta', '04024', 3),
('Itri', '04020', 3),
('Latina', '04100', 3),
('Lenola', '04025', 3),
('Maenza', '04010', 3),
('Minturno', '04026', 3),
('Monte San Biagio', '04020', 3),
('Norma', '04010', 3),
('Pontinia', '04014', 3),
('Ponza', '04027', 3),
('Priverno', '04015', 3),
('Prossedi', '04010', 3),
('Roccagorga', '04010', 3),
('Rocca Massima', '04010', 3),
('Roccasecca dei Volsci', '04010', 3),
('Sabaudia', '04016', 3),
('San Felice Circeo', '04017', 3),
('Santi Cosma e Damiano', '04020', 3),
('Sermoneta', '04013', 3),
('Sezze', '04018', 3),
('Sonnino', '04010', 3),
('Sperlonga', '04029', 3),
('Spigno Saturnia', '04020', 3),
('Terracina', '04019', 3),
('Ventotene', '04031', 3),
('Acquafondata', '03040', 3),
('Acuto', '03010', 3),
('Alatri', '03011', 3),
('Alvito', '03041', 3),
('Amaseno', '03021', 3),
('Anagni', '03012', 3),
('Aquino', '03031', 3),
('Arce', '03032', 3),
('Arnara', '03020', 3),
('Arpino', '03033', 3),
('Atina', '03042', 3),
('Ausonia', '03040', 3),
('Belmonte Castello', '03040', 3),
('Boville Ernica', '03022', 3),
('Broccostella', '03030', 3),
('Campoli Appennino', '03030', 3),
('Casalattico', '03030', 3),
('Casalvieri', '03034', 3),
('Cassino', '03043', 3),
('Castelliri', '03030', 3),
('Castelnuovo Parano', '03040', 3),
('Castrocielo', '03030', 3),
('Castro dei Volsci', '03020', 3),
('Ceccano', '03023', 3),
('Ceprano', '03024', 3),
('Cervaro', '03044', 3),
('Colfelice', '03030', 3),
('Collepardo', '03010', 3),
('Colle San Magno', '03030', 3),
('Coreno Ausonio', '03040', 3),
('Esperia', '03045', 3),
('Falvaterra', '03020', 3),
('Ferentino', '03013', 3),
('Filettino', '03010', 3),
('Fiuggi', '03014', 3),
('Fontana Liri', '03035', 3),
('Fontechiari', '03030', 3),
('Frosinone', '03100', 3),
('Fumone', '03010', 3),
('Gallinaro', '03040', 3),
('Giuliano di Roma', '03020', 3),
('Guarcino', '03016', 3),
('Isola del Liri', '03036', 3),
('Monte San Giovanni Campano', '03025', 3),
('Morolo', '03017', 3),
('Paliano', '03018', 3),
('Pastena', '03020', 3),
('Patrica', '03010', 3),
('Pescosolido', '03030', 3),
('Picinisco', '03040', 3),
('Pico', '03020', 3),
('Piedimonte San Germano', '03030', 3),
('Piglio', '03010', 3),
('Pignataro Interamna', '03040', 3),
('Pofi', '03026', 3),
('Pontecorvo', '03037', 3),
('Posta Fibreno', '03030', 3),
('Ripi', '03027', 3),
('Rocca d\'Arce', '03030', 3),
('Roccasecca', '03038', 3),
('San Biagio Saracinisco', '03040', 3),
('San Donato Val di Comino', '03046', 3),
('San Giorgio a Liri', '03047', 3),
('San Giovanni Incarico', '03028', 3),
('Sant\'Ambrogio sul Garigliano', '03040', 3),
('Sant\'Andrea del Garigliano', '03040', 3),
('Sant\'Apollinare', '03048', 3),
('Sant\'Elia Fiumerapido', '03049', 3),
('Santopadre', '03030', 3),
('San Vittore del Lazio', '03040', 3),
('Serrone', '03010', 3),
('Settefrati', '03040', 3),
('Sgurgola', '03010', 3),
('Sora', '03039', 3),
('Strangolagalli', '03020', 3),
('Supino', '03019', 3),
('Terelle', '03040', 3),
('Torre Cajetani', '03010', 3),
('Torrice', '03020', 3),
('Trevi nel Lazio', '03010', 3),
('Trivigliano', '03010', 3),
('Vallecorsa', '03020', 3),
('Vallemaio', '03040', 3),
('Vallerotonda', '03040', 3),
('Veroli', '03029', 3),
('Vicalvi', '03030', 3),
('Vico nel Lazio', '03010', 3),
('Villa Latina', '03040', 3),
('Villa Santa Lucia', '03030', 3),
('Villa Santo Stefano', '03020', 3),
('Viticuso', '03040', 3);