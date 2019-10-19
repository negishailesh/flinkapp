package flink.process


/*
evictors are used to remove elements from windows once the Trigger fires and before and/or after the window function is called.
two methods are there to evict:
1. evictBefore > applying evict function right after trigger and before window functions.
2. evictAfter > applying evict function after window functions.
 */
object EvictorsInWindow {

}
