SELECT USER
  FROM DUAL;
--==>> SCOTT

DROP TABLE TBL_MEMBER PURGE;
--==>> Table TBL_MEMBER��(��) �����Ǿ����ϴ�.


--�� �ǽ� ���̺� ����
CREATE TABLE TBL_MEMBER
( SID   NUMBER
, NAME  VARCHAR2(30)
, TEL   VARCHAR2(60)
, CONSTRAINT MEMEBER_SID_PK PRIMARY KEY(SID)
);
--==>> Table TBL_MEMBER��(��) �����Ǿ����ϴ�.


--�� ���� ������ �Է�
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(1, 'ȫ�浿', '010-1111-1111');
--==>> 1 �� ��(��) ���ԵǾ����ϴ�.


--�� Ȯ��
SELECT *
  FROM TBL_MEMBER;

--�� Ŀ��
COMMIT;
--==>> Ŀ�� �Ϸ�.


--�� �ڹٿ��� Test003.java ���� �� �ٽ� Ȯ��
SELECT *
  FROM TBL_MEMBER;
--==>> 
/*
1	ȫ�浿	010-1111-1111
2	������	010-5041-0168
*/


UPDATE TBL_MEMBER
SET NAME = '�ֱ浿'
  , TEL = '010-2222-2222'
WHERE SID = 2;

COMMIT;

--�� �ڹٿ��� Test004.java ���� �� �ٽ� Ȯ��
SELECT *
  FROM TBL_MEMBER;
--==>>
/*
1	ȫ�浿	010-1111-1111
2	�ֱ浿 	010-5041-0168
3	������	010-3333-3333
4	���̻�	010-4444-4444
5	������	010-5555-5555
*/

--�� ��ȸ ������ �غ�
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
--> ���� ����

SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;
