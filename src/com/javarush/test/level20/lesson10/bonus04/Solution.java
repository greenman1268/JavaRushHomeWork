package com.javarush.test.level20.lesson10.bonus04;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.LinkedList;
import java.util.List;

/* Свой список
Посмотреть, как реализован LinkedList.
Элементы следуют так: 1->2->3->4  и так 4->3->2->1
По образу и подобию создать Solution.
Элементы должны следовать так:
1->3->7->15
    ->8...
 ->4->9
    ->10
2->5->11
    ->12
 ->6->13
    ->14
Удалили 2 и 9
1->3->7->15
    ->8
 ->4->10
Добавили 16,17,18,19,20 (всегда добавляются на самый последний уровень к тем элементам, которые есть)
1->3->7->15
       ->16
    ->8->17
       ->18
 ->4->10->19
        ->20
Удалили 18 и 20
1->3->7->15
       ->16
    ->8->17
 ->4->10->19
Добавили 21 и 22 (всегда добавляются на самый последний уровень к тем элементам, которые есть.
Последний уровень состоит из 15, 16, 17, 19. 19 последний добавленный элемент, 10 - его родитель.
На данный момент 10 не содержит оба дочерних элемента, поэтому 21 добавился к 10. 22 добавляется в следующий уровень.)
1->3->7->15->22
       ->16
    ->8->17
 ->4->10->19
        ->21

Во внутренней реализации элементы должны добавляться по 2 на каждый уровень
Метод getParent должен возвращать элемент, который на него ссылается.
Например, 3 ссылается на 7 и на 8, т.е.  getParent("8")=="3", а getParent("13")=="6"
Строки могут быть любыми.
При удалении элемента должна удаляться вся ветка. Например, list.remove("5") должен удалить "5", "11", "12"
Итерироваться элементы должны в порядке добавления
Доступ по индексу запрещен, воспользуйтесь при необходимости UnsupportedOperationException
Должно быть наследование AbstractList<String>, List<String>, Cloneable, Serializable
Метод main в тестировании не участвует
*/
public class Solution extends AbstractList<String> implements List<String>, Cloneable, Serializable
{
    public static void main(String[] args)
    {
        List<String> list = new Solution();
        for (int i = 1; i < 16; i++)
        {
            list.add(String.valueOf(i));
        }
      /*  System.out.println("Expected 3, actual is " + ((Solution) list).getParent("8"));
        list.remove("5");
        System.out.println("Expected null, actual is " + ((Solution) list).getParent("11"));*/
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }
    }

    public String getParent(String value)
    {
        //have to be implemented
        return null;
    }

    @Override
    public String get(int index)
    {
        //throw new UnsupportedOperationException();
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public int size()
    {
        return size;
    }

    transient int size = 0;

    /**
     * Pointer to first node.
     * Invariant: (first == null && last == null) ||
     * (first.prev == null && first.item != null)
     */
    transient Node<String> first;

    /**
     * Pointer to last node.
     * Invariant: (first == null && last == null) ||
     * (last.next == null && last.item != null)
     */
    transient Node<String> last;

    /**
     * Constructs an empty list.
     */
    public Solution()
    {
    }

    private static class Node<String>
    {
        String item;
        Node<String> next;
        Node<String> next2;
        Node<String> prev;

        Node(Node<String> prev, String element, Node<String> next, Node<String> next2)
        {
            this.item = element;
            this.next = next;
            this.next2 = next2;
            this.prev = prev;
        }
    }

    private void linkFirst(String e)
    {
        final Node<String> f = first;
        final Node<String> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }

    /**
     * Links e as last element.
     */
    void linkLast(String e)
    {
        final Node<String> l = last;
        final Node<String> newNode = new Node<>(l, e, null, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
        {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    public void addFirst(String e)
    {
        linkFirst(e);
    }

    /**
     * Appends the specified element to the end of this list.
     * <p/>
     * <p>This method is equivalent to {@link #add}.
     *
     * @param e the element to add
     */
    public void addLast(String e)
    {
        linkLast(e);
    }

    private boolean isElementIndex(int index)
    {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index)
    {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkElementIndex(int index)
    {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    Node<String> node(int index)
    {
        // assert isElementIndex(index);

        if (index < (size >> 1))
        {
            Node<String> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else
        {
            Node<String> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }


    public boolean add(String e)
    {
        linkLast(e);
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * {@code i} such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(Object o)
    {
        if (o == null)
        {
            for (Node<String> x = first; x != null; x = x.next)
            {
                if (x.item == null)
                {
                    unlink(x);
                    return true;
                }
            }
        } else
        {
            for (Node<String> x = first; x != null; x = x.next)
            {
                if (o.equals(x.item))
                {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Unlinks non-null node x.
     */
    String unlink(Node<String> x)
    {
        // assert x != null;
        final String element = x.item;
        final Node<String> next = x.next;
        final Node<String> prev = x.prev;

        if (prev == null)
        {
            first = next;
        } else
        {
            prev.next = next;
            x.prev = null;
        }

        if (next == null)
        {
            last = prev;
        } else
        {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }
}
