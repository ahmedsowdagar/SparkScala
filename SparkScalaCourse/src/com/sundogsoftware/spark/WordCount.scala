package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

object WordCount {
  def main(args:Array[String]){
    
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]","WordCount")
    
    val lines = sc.textFile("../book.txt")
    
    val words = lines.flatMap(x=>x.split(" "))
    
    val count = words.countByValue()
    
    count.foreach(println)
  }
}