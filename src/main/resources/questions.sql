INSERT INTO `question`
VALUES (1,'Quel est votre livre préféré ?'),
       (2,'Quel est le nom de belle fille de votre mère ?');

INSERT INTO `user` VALUES (1,'2004-02-28','amaangd@gmail.com','Amaan',_binary '\0','GD','amaanpass','',1),
                          (2,'2005-02-28','a','Zaza',_binary '\0','YOY','b','',2),
                          (3,'2004-06-02','hinala@gmail.com','Hi',_binary '\0','Nala','amaanpass','',2),
                          (4,'2025-12-12','albert@camus.com','Albert',_binary '\0','CAMUS','AlbertPass','L\'Étranger',1),
                          (5,'2025-12-12','simone@existentialiste.fr','Simone',_binary '\0','DEBEAUVOIR','PlusULTRA','La condition humaine',1),
                          (6,'1985-06-15','nouveau.user@testensitech.fr','Jean',_binary '\0','Valjean','MonNouveauMdp123!','Mon Premier Chat',2),
                          (7,'1913-11-07','albert.nouveau@testensitech.fr','Albert',_binary '\0','Camus','$argon2id$v=19$m=65536,t=10,p=1$nLDUUGRH8CNi2pkxVMy4KQ$CiIB+JJEoH7Q+kvno3ITZ2BSRs/yhyigdiDhtq84hf8','hwGcDZdMNKq19/+6W+7jKA==:x6eDqDpn50eDyWRQGTkm4+XGNOhUrbgz8UBCQPvnt+U=',2);
INSERT INTO `address`
VALUES (1,'Goussainville','France','148','95190','Albert Sarraut','Avenue',1);