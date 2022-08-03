package com.uav.ops.utils;

import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class KmlUtils {

	private final static String DIR_NAME = "/wpmz";

	private static String pathRoot;

	@Value("${root.path}")
	public void setPathRoot(String pathRoot) {
		KmlUtils.pathRoot = pathRoot;
	}

	private static final String[] ACTION_ACTUATOR_FUNC = new String[]{"takePhoto", "startRecord", "stopRecord", "hover"};

	public static void setLineWpml(CruiseLineResDTO line, List<CruisePointResDTO> points) {
		try {
			Document root = DocumentHelper.createDocument();
			Element kml = root.addElement("kml", "http://www.opengis.net/kml/2.2");
			kml.addNamespace("wpml", "http://www.dji.com/wpmz/1.0.0");
			Element documentElement = kml.addElement("Document");
			// Mission Configuration
			Element missionElement = documentElement.addElement("wpml:missionConfig");
			missionElement.addElement("wpml:flyToWaylineMode").addText("safely");
			missionElement.addElement("wpml:finishAction").addText("goHome");
			missionElement.addElement("wpml:exitOnRCLost").addText("goContinue");
			missionElement.addElement("wpml:globalTransitionalSpeed").addText("10");
			// Folder
			Element folderElement = documentElement.addElement("Folder");
			folderElement.addElement("wpml:templateId").addText("0");
			folderElement.addElement("wpml:executeHeightMode").addText("relativeToStartPoint");
			folderElement.addElement("wpml:waylineId").addText("0");
			folderElement.addElement("wpml:autoFlightSpeed").addText(String.valueOf(line.getFlySpeed()));
			int i = 0;
			for (CruisePointResDTO point : points) {
				Element placeMarkElement = folderElement.addElement("Placemark");
				Element pointElement = placeMarkElement.addElement("Point");
				pointElement.addElement("coordinates").addText(point.getLng() + "," + point.getLat());
				placeMarkElement.addElement("wpml:index").addText(String.valueOf(i));
				placeMarkElement.addElement("wpml:executeHeight").addText(String.valueOf(point.getPointHeight()));
				placeMarkElement.addElement("wpml:waypointSpeed").addText(String.valueOf(point.getPointSpeed()));
				Element waypointHeadingParamElement = placeMarkElement.addElement("wpml:waypointHeadingParam");
				waypointHeadingParamElement.addElement("wpml:waypointHeadingMode").addText("followWayline");
				Element waypointTurnParamElement = placeMarkElement.addElement("wpml:waypointTurnParam");
				waypointTurnParamElement.addElement("wpml:waypointTurnMode").addText("toPointAndStopWithDiscontinuityCurvature");
				waypointTurnParamElement.addElement("wpml:waypointTurnDampingDist").addText("0");
				// Action group
				Element actionGroupElement = placeMarkElement.addElement("wpml:actionGroup");
				actionGroupElement.addElement("wpml:actionGroupId").addText("0");
				actionGroupElement.addElement("wpml:actionGroupStartIndex").addText("0");
				actionGroupElement.addElement("wpml:actionGroupEndIndex").addText("0");
				actionGroupElement.addElement("wpml:actionGroupMode").addText("sequence");
				Element actionTriggerElement = actionGroupElement.addElement("wpml:actionTrigger");
				actionTriggerElement.addElement("wpml:actionTriggerType").addText("reachPoint");
				Element actionElement = actionGroupElement.addElement("wpml:action");
				actionElement.addElement("wpml:actionId").addText("0");
				actionElement.addElement("wpml:actionActuatorFunc").addText(ACTION_ACTUATOR_FUNC[point.getActionType()]);
				Element actionActuatorFuncParamElement = actionElement.addElement("wpml:actionActuatorFuncParam");
				if (point.getActionType() == 1) {
					actionActuatorFuncParamElement.addElement("wpml:fileSuffix").addText(point.getPointName());
					actionActuatorFuncParamElement.addElement("wpml:payloadPositionIndex").addText("0");
				} else if (point.getActionType() == 2) {
					actionActuatorFuncParamElement.addElement("wpml:fileSuffix").addText(point.getPointName());
					actionActuatorFuncParamElement.addElement("wpml:payloadPositionIndex").addText("0");
				} else if (point.getActionType() == 3) {
					actionActuatorFuncParamElement.addElement("wpml:payloadPositionIndex").addText("0");
				} else {
					actionActuatorFuncParamElement.addElement("wpml:hoverTime").addText("5");
				}
				i++;
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			String path = System.getProperty("user.dir") + pathRoot + line.getId() + DIR_NAME;
			File file = new File(path);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path + "/waylines.wpml"), format);
			xmlWriter.write(root);
			xmlWriter.close();
		} catch (Exception e) {
			throw new CommonException(ErrorCode.LINE_FILE_CREATE_ERROR, "waylines.wpml");
		}
	}

	public static void setLineKml(CruiseLineResDTO line, List<CruisePointResDTO> points) {
		try {
			Document root = DocumentHelper.createDocument();
			Element kml = root.addElement("kml", "http://www.opengis.net/kml/2.2");
			kml.addNamespace("wpml", "http://www.dji.com/wpmz/1.0.0");
			Element documentElement = kml.addElement("Document");
			documentElement.addElement("wpml:createTime").addText(String.valueOf(System.currentTimeMillis()));
			documentElement.addElement("wpml:updateTime").addText(String.valueOf(System.currentTimeMillis()));
			// Mission Configuration
			Element missionElement = documentElement.addElement("wpml:missionConfig");
			missionElement.addElement("wpml:flyToWaylineMode").addText("safely");
			missionElement.addElement("wpml:finishAction").addText("goHome");
			missionElement.addElement("wpml:exitOnRCLost").addText("goContinue");
			missionElement.addElement("wpml:globalTransitionalSpeed").addText("10");
			// Folder
			Element folderElement = documentElement.addElement("Folder");
			folderElement.addElement("wpml:templateType").addText("waypoint");
			folderElement.addElement("wpml:useGlobalTransitionalSpeed").addText("0");
			folderElement.addElement("wpml:templateId").addText("0");
			Element waylineCoordinateSysParamElement = folderElement.addElement("wpml:waylineCoordinateSysParam");
			waylineCoordinateSysParamElement.addElement("wpml:coordinateMode").addText("WGS84");
			waylineCoordinateSysParamElement.addElement("wpml:heightMode").addText("EGM96");
			waylineCoordinateSysParamElement.addElement("wpml:globalHeight").addText(String.valueOf(line.getFlyHeight()));
			waylineCoordinateSysParamElement.addElement("wpml:positioningType").addText("GPS");
			folderElement.addElement("wpml:autoFlightSpeed").addText(String.valueOf(line.getFlySpeed()));
			folderElement.addElement("wpml:transitionalSpeed").addText(String.valueOf(line.getFlySpeed()));
			folderElement.addElement("wpml:gimbalPitchMode").addText("usePointSetting");
			folderElement.addElement("wpml:caliFlightEnable").addText("0");
			Element globalWaypointHeadingParamElement = folderElement.addElement("wpml:globalWaypointHeadingParam");
			globalWaypointHeadingParamElement.addElement("wpml:waypointHeadingMode").addText("followWayline");
			folderElement.addElement("wpml:globalWaypointTurnMode").addText("toPointAndStopWithDiscontinuityCurvature");
			int i = 0;
			for (CruisePointResDTO point : points) {
				Element placeMarkElement = folderElement.addElement("Placemark");
				Element pointElement = placeMarkElement.addElement("Point");
				pointElement.addElement("coordinates").addText(point.getLng() + "," + point.getLat());
				placeMarkElement.addElement("wpml:index").addText(String.valueOf(i));
				placeMarkElement.addElement("wpml:ellipsoidHeight").addText(String.valueOf(point.getPointHeight()));
				placeMarkElement.addElement("wpml:height").addText(String.valueOf(point.getPointHeight()));
				placeMarkElement.addElement("wpml:useGlobalHeight").addText("1");
				placeMarkElement.addElement("wpml:useGlobalSpeed").addText("1");
				placeMarkElement.addElement("wpml:useGlobalHeadingParam").addText("1");
				placeMarkElement.addElement("wpml:useGlobalTurnParam").addText("1");
				placeMarkElement.addElement("wpml:gimbalPitchAngle").addText("0");
				// Action group
				Element actionGroupElement = placeMarkElement.addElement("wpml:actionGroup");
				actionGroupElement.addElement("wpml:actionGroupId").addText("0");
				actionGroupElement.addElement("wpml:actionGroupStartIndex").addText("0");
				actionGroupElement.addElement("wpml:actionGroupEndIndex").addText("0");
				actionGroupElement.addElement("wpml:actionGroupMode").addText("sequence");
				Element actionTriggerElement = actionGroupElement.addElement("wpml:actionTrigger");
				actionTriggerElement.addElement("wpml:actionTriggerType").addText("reachPoint");
				Element actionElement = actionGroupElement.addElement("wpml:action");
				actionElement.addElement("wpml:actionId").addText("0");
				actionElement.addElement("wpml:actionActuatorFunc").addText(ACTION_ACTUATOR_FUNC[point.getActionType()]);
				Element actionActuatorFuncParamElement = actionElement.addElement("wpml:actionActuatorFuncParam");
				if (point.getActionType() == 1) {
					actionActuatorFuncParamElement.addElement("wpml:fileSuffix").addText(point.getPointName());
					actionActuatorFuncParamElement.addElement("wpml:payloadPositionIndex").addText("0");
				} else if (point.getActionType() == 2) {
					actionActuatorFuncParamElement.addElement("wpml:fileSuffix").addText(point.getPointName());
					actionActuatorFuncParamElement.addElement("wpml:payloadPositionIndex").addText("0");
				} else if (point.getActionType() == 3) {
					actionActuatorFuncParamElement.addElement("wpml:payloadPositionIndex").addText("0");
				} else {
					actionActuatorFuncParamElement.addElement("wpml:hoverTime").addText("5");
				}
				i++;
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			String path = System.getProperty("user.dir") + pathRoot + line.getId() + DIR_NAME;
			File file = new File(path);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path + "/template.kml"), format);
			xmlWriter.write(root);
			xmlWriter.close();
		} catch (Exception e) {
			throw new CommonException(ErrorCode.LINE_FILE_CREATE_ERROR, "template.kml");
		}
	}
}
