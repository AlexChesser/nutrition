package com.iifymapp.iifym.Model;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;

import com.iifymapp.iifym.DatabaseHelper;

public class NutritionData extends DatabaseHelper {
	public class Nutrient {
		public String description;
		public Float Value;
		public String Units;
		
		public void setValue(String v, String d){
			
		}
		
	}

	
	public NutritionData(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<HashMap<String, String>> FoodData(String foodID){
		Connect();
		
		Cursor DEF = sqdb.rawQuery("SELECT *, '' as VAL FROM NUTR_DEF ORDER BY cast(SR_Order as int) ASC", null);
        if (DEF != null) {
            DEF.moveToFirst();
        }		
		
		String sql = "SELECT * FROM NUT_DENORM WHERE _id = '"+foodID+"'";
        Cursor c = sqdb.rawQuery(sql, null);
        if (c != null) {
            c.moveToFirst();
        }
		
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        while (DEF.isAfterLast() == false) 
        {
            String NutrNo = "_"+DEF.getString(DEF.getColumnIndex("Nutr_No"));
        	String NutrDesc = DEF.getString(DEF.getColumnIndex("NutrDesc"));
        	String Value = c.getString(c.getColumnIndex(NutrNo));
        	String Num_Dec = DEF.getString(DEF.getColumnIndex("Num_Dec"));
        	String Units = DEF.getString(DEF.getColumnIndex("Units"));
        	
        	HashMap<String, String> map =  new HashMap<String,String>();
        	
        	map.put("Description", NutrDesc);
        	map.put("Value", Value);
        	map.put("Units", Units);
        	map.put("Decimals", Num_Dec);
        	
        	if (Value != null) {
        		list.add(map);
        	}

            DEF.moveToNext();
        }
        
        close();
        c.close();
        DEF.close();
        sqdb.close();
        return list;
	}
	
	public Cursor FoodSearch(String searchterm){
		Connect();

        String where = "";
        if (!searchterm.trim().equals("")) {
        	where = "WHERE Long_Desc LIKE '%"+searchterm.trim()+"%'";
        }
        
        String sql = "	SELECT   fd._id, " +
        						"fg.FdGrp_Desc, " +
        						"fd.Long_Desc, " +
        						"ifnull(fd._208,0) as kcal, " +
        						"cast(ifnull(fd._203,0) as int) || 'g' as Pro, " +
        						"cast(ifnull(fd._204,0) as int) || 'g' as Fat, " +
        						"cast(ifnull(fd._205,0) as int) || 'g' as Carb, " +
        						"cast(ifnull(fd._291,0) as int) || 'g' as Fibre " +
        			" FROM NUT_DENORM fd " + 
        				"INNER JOIN FD_GROUP fg ON fd.FdGrp_Cd = fg.FdGrp_Cd " +
        			  where +
        			" ORDER BY FdGrp_Desc, Long_Desc ASC";
        //android.util.Log.i("SQL", sql);
        // Execute query
        Cursor c = sqdb.rawQuery(sql, null);
        if (c != null) {
            c.moveToFirst();
        }
        close();
        sqdb.close();
        return c;
	}
	
}
