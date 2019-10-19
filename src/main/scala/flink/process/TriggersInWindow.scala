package flink.process

object TriggersInWindow {
  /*
  Triggers determine when a window is ready to be processed.

  All window assigners comes with default Triggers.

  Trigger have five methods:
  > onElement method > every element of window . we can write login for every elements of window.
  > onEventTime
  > onProcessingTime
  > onMerge
  > clear

  first three are important because of their return type.And last two returns null

  first 3 return a triggerResult.ANd trigger Result can be of following type:
      * CONTINUE  do nothing.
      * FIRE  trigger the computation.
      * PURGE clear contents of windows.
      * FIRE_AND_PURGE trigger the computation and clear content of window after it.
   */
  def main(args:Array[String]):Unit = {
  }
}
