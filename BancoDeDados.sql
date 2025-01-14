PGDMP  #                    |            loja    17.2    17.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    16387    loja    DATABASE     {   CREATE DATABASE loja WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE loja;
                     postgres    false            �            1259    16388    estado    TABLE     z   CREATE TABLE public.estado (
    id bigint NOT NULL,
    nome character varying(255),
    sigla character varying(255)
);
    DROP TABLE public.estado;
       public         heap r       postgres    false            �            1259    16395 
   estado_seq    SEQUENCE     t   CREATE SEQUENCE public.estado_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.estado_seq;
       public               postgres    false            �            1259    16396    produto    TABLE     U  CREATE TABLE public.produto (
    id bigint NOT NULL,
    promocao character varying(255),
    nome character varying(255),
    preco double precision,
    total double precision,
    unidade_medida character varying(255),
    valor_desconto double precision NOT NULL,
    valor_frete double precision,
    tamanho character varying(255)
);
    DROP TABLE public.produto;
       public         heap r       postgres    false            �            1259    16403    produto_seq    SEQUENCE     u   CREATE SEQUENCE public.produto_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.produto_seq;
       public               postgres    false            �          0    16388    estado 
   TABLE DATA           1   COPY public.estado (id, nome, sigla) FROM stdin;
    public               postgres    false    217   Y       �          0    16396    produto 
   TABLE DATA           y   COPY public.produto (id, promocao, nome, preco, total, unidade_medida, valor_desconto, valor_frete, tamanho) FROM stdin;
    public               postgres    false    219   �       �           0    0 
   estado_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.estado_seq', 51, true);
          public               postgres    false    218            �           0    0    produto_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.produto_seq', 751, true);
          public               postgres    false    220            ]           2606    16394    estado estado_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.estado DROP CONSTRAINT estado_pkey;
       public                 postgres    false    217            _           2606    16402    produto produto_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.produto DROP CONSTRAINT produto_pkey;
       public                 postgres    false    219            �   5   x�3�N�+ITpN,I,��K�v�2�H,J�KL��2�r��A�=... ���      �   �   x���;
1���)�.yeKA�PXQK�A�0�d�P�b/f��DA�"�|�d����W�0w��>"��f#�A�������^�)��'8��h(���`>E"7��
��}p>�qg����K�]�ֹ��Q�S���P!zpCM �I9��5�%e��[�M�HJ��v焋�=��(:m�     