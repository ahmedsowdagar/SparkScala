package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import math._

object MaxTemperatures{
  
  def parseLine(line:String)= {
    val fields = line.split(",");
    val location = fields(0)
    val date = fields(1)
    val entryType = fields(2)
    val temperature = fields(3).toInt
    (location,date,entryType,temperature)
  }
 
  
  
  def main(args:Array[String])
  {
    Logger.getLogger("org").setLevel(Level.ERROR);
    
    val sc = new SparkContext("local[*]","MaxTemperaturesContext")
    
    val lines = sc.textFile("../1800.csv");
   
 //   lines.foreach(println);
    
    val parsedLines = lines.map(parseLine);
    
    //Get maximum temperature out of all values
    val maxTemps = parsedLines.filter(x=>x._3 == "TMAX")
    
    //print
 //   maxTemps.foreach(println)
    
    //Convert to only two entries that is station and temperature
    val reducedMapSet = maxTemps.map(x=>(x._1,x._4))
    
    //print
  // reducedMapSet.foreach(println)
    
    //Get highest for each station
    val maxTemperatureByStation = reducedMapSet.reduceByKey((x,y)=> max(x,y))
    
    maxTemperatureByStation.foreach(println)
  }
}