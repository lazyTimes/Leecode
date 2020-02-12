package LRUQueue;

import java.util.LinkedHashMap;

/**
 * 设计并实现最近最少经用（LRU）缓存的数据结构。它应该支持以下操作：get 和 put。
 * get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
 * put(key, value) - 如果键不存在，请设置或插入值。当缓存达到其容量时，它应该在插入新项目之前，
 * 使最近最少使用的项目无效。
 * 进阶：
 * 你是否可以在 O(1) 时间复杂度内执行两项操作？
 * 示例：
 * LFUCache cache = new LFUCache( 2  capacity (缓存容量)  );
*
*
*
*cache.put(1,1);
*
*cache.put(2,2);
*
*cache.get(1);       // 返回 1
*
*cache.put(3,3);    // 去除 key 2
*
*cache.get(2);       // 返回 -1 (未找到key 2)
*
*cache.get(3);       // 返回 3
*
*cache.put(4,4);    // 去除 key 1
*
*cache.get(1);       // 返回 -1 (未找到 key 1)
*
*cache.get(3);       // 返回 3
*
*cache.get(4);       // 返回 4
*
 * @program: Leecode
 * @description: LRU算法的单链表实现
 * @author: zhaoxudong
 * @create: 2020-02-12 17:58
 **/
public class LinkedLRUQueue<K, V> {

    public static class LRUQueue{
        private String key;
        private Object value;
        private LRUQueue next;

        public LRUQueue(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public LRUQueue getNext() {
            return next;
        }

        public void setNext(LRUQueue next) {
            this.next = next;
        }
    }

    /**
     * 默认大小为16
     */
    private int capita = 16;
    /**
     * 当前大小
     */
    private int size = 0;

    /**
     * 头结点
     */
    private LRUQueue head;

    /**
     * 初始化指定大小
     * @param capita
     */
    public LinkedLRUQueue(int capita) {
        if(capita <= 0){
            throw new AssertionError("初始化不能为0或者负数");
        }
        this.capita = capita;
    }

    public Object get(String key){
        LRUQueue cur = head;
        LRUQueue pre = head;
        if(head == null){
            return null;
        }
        // 如果如果是头部，直接返回
        if(cur.key.equals(key)){
            return cur.value;
        }
        cur = cur.next;
        while(cur != null){
            if(cur.key.equals(key)){
                break;
            }
            pre = cur;
            cur = cur.next;
        }

        if(cur == null){
            return null;
        }

        pre.next = cur.next;
        // 删除之后插入头节点
        cur.next = head;
        head = cur;
        return cur.value;
    }


    /**
     * 添加一个键值对
     * 1. 判断容量是否为1，如果为1 直接添加到头节点
     * 2. 设置一个迭代对象cur和pre
     * 3. 迭代，迭代过程中查找是否有已存在的key，存在则挑出循环
     * 4. 查找是否存在key，不存在则
     * @param key
     * @param Value
     */
    public void put(String key, Object Value){
        // 如果初始容量为1，则只能设置head和首节点相同
        if(capita == 1){
            head = new LRUQueue(key, Value);
        }
        LRUQueue cur = head;
        LRUQueue pre = head;
        // 如果头结点为空，则设置之后返回
        if(head == null){
            head = new LRUQueue(key, Value);
            return;
        }

        // 如果节点已经存在，则去除掉上一个
        if(head.getKey().equals(key)){
            head.setValue(Value);
            return;
        }
        // 迭代到下一个节点
        cur = cur.next;
        while(cur != null){
            if(cur.key.equals(key)){
                break;
            }
            pre = cur;
            cur = cur.next;
        }
        // 代表要插入的节点的 key 已存在，则进行 value 的更新
        // 以及把它放到第一个节点去
        if(cur != null){
            cur.value = Value;
            pre.next = cur.next;
            cur.next = head;
            head = cur;
        }else{
            // 创建一个新的节点
            LRUQueue lruQueue = new LRUQueue(key, Value);
            // 该节点不存在，需要判断插入后会不会溢出
            if(size >= capita){
                // 直接淘汰最后一个节点
                cur = head;
                while(cur.next != null && cur.next.next != null){
                    cur = cur.next;
                }
                cur.next = null;
                lruQueue.next = cur;
                head = lruQueue;

            }
        }
    }




    public static void main(String[] args) {

    }
}
