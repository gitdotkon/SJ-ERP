DELIMITER $$

USE `dsm`$$

DROP PROCEDURE IF EXISTS `proc_plan`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_plan`(IN so VARCHAR(255))
BEGIN
SELECT mm.`partCode` partCode,SUM(mm.requiredQty)/COUNT(1) requiredQty,
SUM(poi.plannedQty-poi.finishedQty)/COUNT(1) processingQty,SUM(ma.requiredQty) reservedQty
FROM MRPModel mm
LEFT JOIN ProductionOrderItem poi ON mm.`partCode`=poi.`partCode`
LEFT JOIN MRPModel ma ON mm.`partCode`=ma.`partCode` AND ma.planned=TRUE
WHERE mm.mprType=0 AND mm.planned = FALSE AND FIND_IN_SET(mm.salesorder,so)
GROUP BY mm.`partCode`;
END$$

DELIMITER ;