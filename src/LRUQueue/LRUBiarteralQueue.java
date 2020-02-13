package LRUQueue;

import sun.java2d.pipe.LCDTextRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: Leecode
 * @description: 双向链表实现LRU算法
 * @author: zhaoxudong
 * @create: 2020-02-13 11:32
 **/
public class LRUBiarteralQueue {

    class LRUNode{
        private String key;

        private Object value;

        private LRUNode pre;

        private LRUNode next;

        public LRUNode(String key, Object value) {
            this.key = key;
            this.value = value;
        }


    }

    private LRUNode head;
    private LRUNode tail;

    private int capity = 16;


    Map<String, LRUNode> map = new HashMap<>();


    public Object get(String key){
        LRUNode node = map.get(key);
        if(node != null){
            removeAndInsert(node);
            return node.value;
        }
        return null;
    }



    public void put(String key, Object value){
        if(capity == 1){
            head = new LRUNode(key, value);
        }
        LRUNode pre = head;
        LRUNode next = head;
        if(head == null){
            head = new LRUNode(key, value);
            tail = head;
            map.put(key, head);
            return;
        }
        LRUNode node = map.get(key);

        if(node != null){
            // 更新
            node.value = value;
            removeAndInsert(node);
        }else{
            // 创建新节点
            LRUNode temp = new LRUNode(key, value);
            // 如果溢出了
            if(map.size() >= capity){
                // 先把它从哈希表删除
                map.remove(tail.key);
                // 删除尾部节点
                tail = tail.pre;
                tail.next = null;
            }
            map.put(key, temp);
            // 插入
            temp.next = head;
            head.pre = temp;
            head = temp;

        }





    }

    private void removeAndInsert(LRUNode node) {
        // 特殊情况判断是头结点还是尾部节点
        if(node == head){
            return;
        }else if(node == tail){
            tail = node.pre;
            tail.next = null;
        }else{
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        // 插入到头结点
        node.next = head;
        node.pre = null;
        head.pre = node;
        head = node;
    }

    public static void main(String[] args) {

    }


}
