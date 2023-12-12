package myshop.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import myshop.Util.DbUtil;

public class ProductDAO {
    public static ObservableList<Product> getProductsList() {

        String selectAll = "SELECT * FROM products";
        ObservableList<Product> prodList = FXCollections.observableArrayList();

        try {
            ResultSet rs = DbUtil.dbExecuteQuery(selectAll);
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setDesignation(rs.getString("designation_produit"));
                product.setPrice(rs.getDouble("prix"));
                product.setQuantity(rs.getInt("quantity"));
                prodList.add(product);
            }
        } catch (Exception e) {
            System.out.println("Sql operation failed");
        }
        return prodList;
    }

    public static void updateProd(int id, String designation, double prix, int quantity) throws SQLException {
        String statement = "UPDATE products\n" +
                " SET designation_produit = '" + designation +
                "', prix = " + prix + ", quantity = " + quantity + " WHERE id = " + id + "; \n";
        try {
            DbUtil.dbExecuteUpdate(statement);
        } catch (Exception e) {
            System.out.println("problem executing update query " + e);
            throw e;
        }
    }

    public static void deleteProd(int id) throws SQLException {
        String statement = "   DELETE FROM products\n" +
                "         WHERE id =" + id + ";\n";
        try {
            DbUtil.dbExecuteUpdate(statement);
        } catch (SQLException e) {
            System.out.print("problem executing delete query " + e);
            throw e;
        }
    }

    public static void achatVenteProd(int id, String operation) throws SQLException {
        char o = operation.equals("achat") ? '+' : '-';
        String statement = "   UPDATE products\n" +
                "         SET quantity = quantity " + o + " 1\n" +
                " WHERE id = " + id + ";\n";
        try {
            DbUtil.dbExecuteUpdate(statement);
        } catch (Exception e) {
            System.out.println("problem executing update query " + e);
            throw e;
        }
    }

    public static void insertProd(String designation, double price, int quantity)
            throws SQLException, ClassNotFoundException {
        // Declare a DELETE statement
        String stmt = "INSERT INTO products\n" +
                "(designation_produit, prix, quantity)\n" +
                "VALUES\n" +
                "('" + designation + "', '" + price + "', '" + quantity + "');\n";
        // Execute DELETE operation
        try {
            DbUtil.dbExecuteUpdate(stmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }

}
