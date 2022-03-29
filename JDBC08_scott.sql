;
SELECT USER
FROM DUAL;
--==>> SCOTT

SELECT *
  FROM TBL_MEMBER
 ORDER BY SID;
--==>>
/*
2	��ȣ��	010-1111-1111
3	���̻�	010-2222-2222
4	�Ӽҹ�	010-3333-3333
8	����	010-4444-4444
9	������	010-5555-5555
10	������	010-6666-6666
30	������	010-3030-3030
31	������	010-3131-3131
*/

TRUNCATE TABLE TBL_MEMBER;
--==>> Table TBL_MEMBER��(��) �߷Ƚ��ϴ�.

SELECT *
  FROM TBL_MEMBER
ORDER BY SID;
--==>> ��ȸ ��� ����

--�� CallableStatement �ǽ��� ���� ���ν��� �ۼ�
--   ���ν��� ��   : PRC_MEMBERINSERT
--   ���ν��� ��� : TBL_MEMBER ���̺� �����͸� �Է��ϴ� ���ν���
CREATE OR REPLACE PROCEDURE PRC_MEMBERINSERT
( VNAME IN TBL_MEMBER.NAME%TYPE
, VTEL  IN TBL_MEMBER.TEL%TYPE
)
IS
    -- �ֿ� ���� ����
    VSID    TBL_MEMBER.SID%TYPE;
BEGIN
    -- ���� SID�� �ִ밪 ������
    SELECT NVL(MAX(SID), 0) INTO VSID
    FROM TBL_MEMBER;

    -- ������ �Է�(INSERT ������ ����)
    INSERT INTO TBL_MEMBER(SID, NAME, TEL)
    VALUES((VSID+1), VNAME, VTEL);
    
    --Ŀ��
    COMMIT;
END;
--==>> Procedure PRC_MEMBERINSERT��(��) �����ϵǾ����ϴ�.

--�� ������ ���ν��� �׽�Ʈ(Ȯ��)
EXEC PRC_MEMBERINSERT('��ȣ��', '010-1111-1111');
--==>> PL/SQL ���ν����� ���������� �Ϸ�Ǿ����ϴ�.

--�� ���̺� ��ȸ
SELECT SID, NAME, TEL
  FROM TBL_MEMBER
 ORDER BY SID;
 
 
--�� JDBC08�� Test001 ���� �� Ȯ��
SELECT *
  FROM TBL_MEMBER
 ORDER BY SID;
--==>>
/*
1	��ȣ��	010-1111-1111
2	������	010-2222-2222
3	�̿���	010-3333-3333
4	ȫ����	010-4444-4444
5	������	010-5555-5555
*/

--�� CallabelStatement �ǽ��� ���� ���ν��� �ۼ�
--   ���ν��� ��   : PRC_MEMBERSELECT
--   ���ν��� ��� : TBL_MEMBER ���̺��� �����͸� �о���� ���ν���
--   �� ��SYS_REFUCURSOR�� �ڷ����� �̿��Ͽ� Ŀ�� �ٷ��

CREATE OR REPLACE PROCEDURE PRC_MEMBERSELECT
( VRESULT   OUT SYS_REFCURSOR
)
IS
    -- �ֿ� ���� ����
BEGIN
    OPEN VRESULT FOR
        SELECT SID, NAME, TEL
          FROM TBL_MEMBER
         ORDER BY SID;
         
    --CLOSE VRESULT;
    
    -- COMMIT;
END;
--==>> Procedure PRC_MEMBERSELECT��(��) �����ϵǾ����ϴ�.








