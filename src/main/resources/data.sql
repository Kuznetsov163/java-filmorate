

MERGE INTO mparating AS target
USING (SELECT 1 AS ID, 'G' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO mparating AS target
USING (SELECT 2 AS ID, 'PG' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO mparating AS target
USING (SELECT 3 AS ID, 'PG-13' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO mparating AS target
USING (SELECT 4 AS ID, 'R'
AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO mparating AS target
USING (SELECT 5 AS ID, 'NC-17' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);



MERGE INTO GENRE AS target
USING (SELECT 1 AS ID, 'Комедия' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO GENRE AS target
USING (SELECT 2 AS ID, 'Драма' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO GENRE AS target
USING (SELECT 3 AS ID, 'Мультфильм' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO GENRE AS target
USING (SELECT 4 AS ID, 'Триллер' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO GENRE AS target
USING (SELECT 5 AS ID, 'Документальный' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);

MERGE INTO GENRE AS target
USING (SELECT 6 AS ID, 'Боевик' AS NAME) AS source
ON target.ID = source.ID
WHEN MATCHED THEN
    UPDATE SET target.NAME = source.NAME
WHEN NOT MATCHED THEN
    INSERT (ID, NAME) VALUES (source.ID, source.NAME);