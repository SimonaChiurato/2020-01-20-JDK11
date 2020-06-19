package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> listRole() {
		String sql = "SELECT DISTINCT role from authorship ORDER BY role ASC";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(res.getString("role"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void getArtist(Map<Integer, Artist> idMap, String role) {
		String sql = "SELECT DISTINCT a1.artist_id as id ,name FROM artists a1, authorship au WHERE a1.artist_id=au.artist_id AND role=?";
	
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				idMap.put(res.getInt("id"),new Artist(res.getInt("id"), res.getString("name")) );
			}
			conn.close();
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	public List<Adiacenza> getAdiacenze(Map<Integer, Artist> idMap, String role) {
		String sql = "SELECT a1.artist_id AS id1, a2.artist_id AS id2, COUNT(eo1.exhibition_id) AS peso from artists a1, artists a2, authorship au1, authorship au2 ,exhibition_objects eo1,exhibition_objects eo2 " + 
				"WHERE a1.artist_id=au1.artist_id AND a2.artist_id=au2.artist_id AND a1.artist_id>a2.artist_id AND au1.role= au2.role " + 
				"AND au1.role= ? AND au1.object_id=eo1.object_id AND au2.object_id=eo2.object_id AND eo1.exhibition_id=eo2.exhibition_id " + 
				"GROUP BY id1,id2";
	List<Adiacenza> result= new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int id1= res.getInt("id1");
				int id2= res.getInt("id2");
				if(idMap.containsKey(id1) && idMap.containsKey(id2)) {
					Adiacenza a= new Adiacenza(idMap.get(id1), idMap.get(id2), res.getDouble("peso"));
					result.add(a);
				}
				
				
			}
			conn.close();
		return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
