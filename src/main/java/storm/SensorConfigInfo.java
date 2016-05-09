package storm;

import java.util.Properties;

/**
 * Senosor config class
 * 用于存放SensorConfig.properties中存放的所有要素在数据库中的名称信息以及SOS的地址URL
 * Created by Yuan on 2016/4/20.
 */
public class SensorConfigInfo {
    String URL="url";
    String HOST="host";
    String DRIVER="driver";
    String USER="user";
    String PASSWORD="password";

    String DBNAME="dbName";

    String SENSORTABLENAME="sensorTableName";
    String ST_ID="id";
    String ST_IP="ip";
    String ST_PORT="port";
    String ST_STARTINGADDRESS="startingAddress";
    String ST_SLAVEADDRESS="slaveAddress";
    String ST_SOCKETOUTOFTIME="socketOutTime";
    String ST_SLEEPTIME="sleeptime";
    String ST_LOGFILE="logFile";
    String ST_LON="lon";
    String ST_LAT="lat";
    String ST_PROTOCOL="protocol";

    String PROPERTYTABLENAME="propertyTableName";
    String PT_OBJECTID="objectID";
    String PT_PID="pid";
    String PT_PROPERTYID="propertyID";
    String PT_PROPERTYNAME="propertyName";
    String PT_PROPERTYUNIT="propertyUnit";
    String PT_PROPERTYSTARTPOS="propertyStartPos";
    String PT_PROPERTYSTARTLEN="propertyLen";
    private static String url;
    private static String host;
    private static String driver;
    private static String user;
    private static String password;

    private static String dbName;

    private static String sensorTableName;
    private static String st_id;
    private static String st_ip;
    private static String st_port;
    private static String st_startingAddress;
    private static String st_slaveAddress;
    private static String st_socketOutofTime;
    private static String st_sleepTime;
    private static String st_logFile;
    private static String st_lon;
    private static String st_lat;
    private static String st_protocal;

    private static String propertyTableName;
    private static String pt_objectID;
    private static String pt_PID;
    private static String pt_propertyID;
    private static String pt_propertyName;
    private static String pt_propertyUnit;
    private static String pt_propertyStartPos;
    private static String pt_propertyLen;

    public SensorConfigInfo(Properties properties) {
        setUrl(properties.getProperty(URL));
        setDriver(properties.getProperty(DRIVER));
        setHost(properties.getProperty(HOST));
        setUser(properties.getProperty(USER));
        setPassword(properties.getProperty(PASSWORD));

        setDbName(properties.getProperty(DBNAME));
        setSensorTableName(properties.getProperty(SENSORTABLENAME));
        setSt_id(properties.getProperty(ST_ID));
        setSt_ip(properties.getProperty(ST_IP));
        setSt_port(properties.getProperty(ST_PORT));
        setSt_startingAddress(properties.getProperty(ST_STARTINGADDRESS));
        setSt_slaveAddress(properties.getProperty(ST_SLAVEADDRESS));
        setSt_socketOutofTime(properties.getProperty(ST_SOCKETOUTOFTIME));
        setSt_sleepTime(properties.getProperty(ST_SLEEPTIME));
        setSt_logFile(properties.getProperty(ST_LOGFILE));
        setSt_lat(properties.getProperty(ST_LAT));
        setSt_lon(properties.getProperty(ST_LON));
        setSt_protocal(properties.getProperty(ST_PROTOCOL));

        setPropertyTableName(properties.getProperty(PROPERTYTABLENAME));
        setPt_objectID(properties.getProperty(PT_OBJECTID));
        setPt_PID(properties.getProperty(PT_PID));
        setPt_propertyID(properties.getProperty(PT_PROPERTYID));
        setPt_propertyName(properties.getProperty(PT_PROPERTYNAME));
        setPt_propertyUnit(properties.getProperty(PT_PROPERTYUNIT));
        setPt_propertyStartPos(properties.getProperty(PT_PROPERTYSTARTPOS));
        setPt_propertyLen(properties.getProperty(PT_PROPERTYSTARTLEN));
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        SensorConfigInfo.url = url;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        SensorConfigInfo.host = host;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        SensorConfigInfo.driver = driver;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        SensorConfigInfo.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SensorConfigInfo.password = password;
    }

    public static String getSensorTableName() {
        return sensorTableName;
    }

    public static void setSensorTableName(String sensorTableName) {
        SensorConfigInfo.sensorTableName = sensorTableName;
    }

    public static String getSt_id() {
        return st_id;
    }

    public static void setSt_id(String st_id) {
        SensorConfigInfo.st_id = st_id;
    }

    public static String getSt_ip() {
        return st_ip;
    }

    public static void setSt_ip(String st_ip) {
        SensorConfigInfo.st_ip = st_ip;
    }

    public static String getSt_port() {
        return st_port;
    }

    public static void setSt_port(String st_port) {
        SensorConfigInfo.st_port = st_port;
    }

    public static String getSt_startingAddress() {
        return st_startingAddress;
    }

    public static void setSt_startingAddress(String st_startingAddress) {
        SensorConfigInfo.st_startingAddress = st_startingAddress;
    }

    public static String getSt_slaveAddress() {
        return st_slaveAddress;
    }

    public static void setSt_slaveAddress(String st_slaveAddress) {
        SensorConfigInfo.st_slaveAddress = st_slaveAddress;
    }

    public static String getSt_socketOutofTime() {
        return st_socketOutofTime;
    }

    public static void setSt_socketOutofTime(String st_socketOutofTime) {
        SensorConfigInfo.st_socketOutofTime = st_socketOutofTime;
    }

    public static String getSt_sleepTime() {
        return st_sleepTime;
    }

    public static void setSt_sleepTime(String st_sleepTime) {
        SensorConfigInfo.st_sleepTime = st_sleepTime;
    }

    public static String getSt_logFile() {
        return st_logFile;
    }

    public static void setSt_logFile(String st_logFile) {
        SensorConfigInfo.st_logFile = st_logFile;
    }

    public static String getSt_lon() {
        return st_lon;
    }

    public static void setSt_lon(String st_lon) {
        SensorConfigInfo.st_lon = st_lon;
    }

    public static String getSt_lat() {
        return st_lat;
    }

    public static void setSt_lat(String st_lat) {
        SensorConfigInfo.st_lat = st_lat;
    }

    public static String getSt_protocal() {
        return st_protocal;
    }

    public static void setSt_protocal(String st_protocal) {
        SensorConfigInfo.st_protocal = st_protocal;
    }

    public static String getPropertyTableName() {
        return propertyTableName;
    }

    public static void setPropertyTableName(String propertyTableName) {
        SensorConfigInfo.propertyTableName = propertyTableName;
    }

    public static String getPt_objectID() {
        return pt_objectID;
    }

    public static void setPt_objectID(String pt_objectID) {
        SensorConfigInfo.pt_objectID = pt_objectID;
    }

    public static String getPt_PID() {
        return pt_PID;
    }

    public static void setPt_PID(String pt_PID) {
        SensorConfigInfo.pt_PID = pt_PID;
    }

    public static String getPt_propertyID() {
        return pt_propertyID;
    }

    public static void setPt_propertyID(String pt_propertyID) {
        SensorConfigInfo.pt_propertyID = pt_propertyID;
    }

    public static String getPt_propertyName() {
        return pt_propertyName;
    }

    public static void setPt_propertyName(String pt_propertyName) {
        SensorConfigInfo.pt_propertyName = pt_propertyName;
    }

    public static String getPt_propertyUnit() {
        return pt_propertyUnit;
    }

    public static void setPt_propertyUnit(String pt_propertyUnit) {
        SensorConfigInfo.pt_propertyUnit = pt_propertyUnit;
    }

    public static String getPt_propertyStartPos() {
        return pt_propertyStartPos;
    }

    public static void setPt_propertyStartPos(String pt_propertyStartPos) {
        SensorConfigInfo.pt_propertyStartPos = pt_propertyStartPos;
    }

    public static String getPt_propertyLen() {
        return pt_propertyLen;
    }

    public static void setPt_propertyLen(String pt_propertyLen) {
        SensorConfigInfo.pt_propertyLen = pt_propertyLen;
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        SensorConfigInfo.dbName = dbName;
    }
}
