# Java Sample

## 为什么要学习 Java 8

- 以前
```java
Collections.sort(inventory, new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
    }
});
```

- 现在
```java
// 给库存排序，按重量
inventory.sort(comparing(Apple::getWeight));
```
