import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

class DB {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    DB() throws Exception {
        InputStream ips = null;
        ips = DB.class.getResourceAsStream("DBConfig.properties");
        Properties prop = new Properties();
        prop.load(ips);
        String Driver = prop.getProperty("Driver");
        String Url = prop.getProperty("Url");
        String User = prop.getProperty("User");
        String Password = prop.getProperty("Password");

        Class.forName(Driver);
        conn = DriverManager.getConnection(Url, User, Password);
    }

    private ResultSet queryData(String sql) throws SQLException {
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        return rs;
    }

    private void Update(String sql) throws SQLException {
        pstmt = conn.prepareStatement(sql);
        pstmt.execute();
    }

    void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                rs = null;
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                pstmt = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            }
        }
    }

    void createTable(String tableName) throws SQLException {
        String sql = "CREATE TABLE `" + tableName + "` (\n" +
                "`word`  varchar(255) NOT NULL ,\n" +
                "`trans`  varchar(255) NOT NULL ,\n" +
                "`phonetic`  varchar(255) NULL ,\n" +
                "PRIMARY KEY (`word`)\n" +
                ")\n" +
                "DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci\n" +
                ";\n";
        Update(sql);
    }

    void addWordList(String tableName, List<Word> wordList) throws SQLException {
        for (Word word :
                wordList) {
            String sql = "INSERT INTO `web`.`" + tableName + "` (`word`, `trans`, `phonetic`) VALUES (\"" +
                    word.getWord() + "\", \"" + word.getTrans() + "\", \"" + word.getPhonetic() + "\")";
            Update(sql);
        }
    }
}
