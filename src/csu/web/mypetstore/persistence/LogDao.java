package csu.web.mypetstore.persistence;


import csu.web.mypetstore.domain.LogData;

public interface LogDao {
    boolean insertLog(LogData logData);
}
