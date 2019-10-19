package flink.pojoClass


import java.util.HashMap
import java.util.Map
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import scala.beans.{BeanProperty, BooleanBeanProperty}
import com.fasterxml.jackson.annotation.JsonProperty
import scala.collection.JavaConversions._




@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(Array("data"))
class JsonMessage(datum :Any) {

  @JsonProperty("data")
  @BeanProperty
  var data: Any =  datum




}