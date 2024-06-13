CREATE TABLE "Image"
(
    image_id          int PRIMARY KEY NOT NULL,
    main_image_id     int NULL,
    file_name         char(50) NOT NULL,
    file_path         char(50) NOT NULL,
    file_type         char(50) NOT NULL,
    date_imported     char(50) NOT NULL,
    hash_value        char(64) NOT NULL,
    hash_length       int NOT NULL,
    hash_algorithm_id int NOT NULL
);

ALTER TABLE public."Image"
    ADD CONSTRAINT fk_main_image
        FOREIGN KEY (main_image_id) REFERENCES public."Image"(image_id);