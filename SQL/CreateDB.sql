/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     2018-10-03 13:21:13                          */
/*==============================================================*/

SET schema 'tinderroulette';

/*==============================================================*/
/* Table: ACTIVITIES                                            */
/*==============================================================*/
create table ACTIVITIES (
   ID_ACTIVITY          SERIAL               not null,
   ID_CLASS             TEXT                 not null,
   CIP_IN_CHARGE        CHAR(8)              null,
   NB_PARTNERS          INT4                 not null,
   constraint PK_ACTIVITIES primary key (ID_ACTIVITY)
);

/*==============================================================*/
/* Index: ACTIVITIES_PK                                         */
/*==============================================================*/
create unique index ACTIVITIES_PK on ACTIVITIES (
ID_ACTIVITY
);

/*==============================================================*/
/* Index: USERACTIVITY_FK                                       */
/*==============================================================*/
create  index USERACTIVITY_FK on ACTIVITIES (
CIP_IN_CHARGE
);

/*==============================================================*/
/* Index: HASCLASS_FK                                           */
/*==============================================================*/
create  index HASCLASS_FK on ACTIVITIES (
ID_CLASS
);

/*==============================================================*/
/* Table: AP                                                    */
/*==============================================================*/
create table AP (
   ID_AP                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   constraint PK_AP primary key (ID_AP)
);

/*==============================================================*/
/* Index: AP_PK                                                 */
/*==============================================================*/
create unique index AP_PK on AP (
ID_AP
);

/*==============================================================*/
/* Table: APP                                                   */
/*==============================================================*/
create table APP (
   ID_APP               TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   constraint PK_APP primary key (ID_APP)
);

/*==============================================================*/
/* Index: APP_PK                                                */
/*==============================================================*/
create unique index APP_PK on APP (
ID_APP
);

/*==============================================================*/
/* Table: CLASSES                                               */
/*==============================================================*/
create table CLASSES (
   ID_CLASS             TEXT                 not null,
   ID_AP                TEXT                 null,
   ID_APP               TEXT                 null,
   constraint PK_CLASSES primary key (ID_CLASS)
);

/*==============================================================*/
/* Index: CLASSES_PK                                            */
/*==============================================================*/
create unique index CLASSES_PK on CLASSES (
ID_CLASS
);

/*==============================================================*/
/* Index: HASAP_FK                                              */
/*==============================================================*/
create  index HASAP_FK on CLASSES (
ID_AP
);

/*==============================================================*/
/* Index: HASAPP_FK                                             */
/*==============================================================*/
create  index HASAPP_FK on CLASSES (
ID_APP
);

/*==============================================================*/
/* Table: FRIENDLIST                                            */
/*==============================================================*/
create table FRIENDLIST (
   CIP                  CHAR(8)              not null,
   FRIEND_CIP           CHAR(8)              not null,
   constraint PK_FRIENDLIST primary key (CIP, FRIEND_CIP)
);

/*==============================================================*/
/* Index: FRIENDLIST_PK                                         */
/*==============================================================*/
create unique index FRIENDLIST_PK on FRIENDLIST (
CIP,
FRIEND_CIP
);

/*==============================================================*/
/* Index: FRIENDLIST_FK                                         */
/*==============================================================*/
create  index FRIENDLIST_FK on FRIENDLIST (
CIP
);

/*==============================================================*/
/* Index: FRIENDLIST2_FK                                        */
/*==============================================================*/
create  index FRIENDLIST2_FK on FRIENDLIST (
FRIEND_CIP
);

/*==============================================================*/
/* Table: GROUPS                                                */
/*==============================================================*/
create table GROUPS (
   ID_GROUP             SERIAL             not null,
   ID_GROUP_TYPE        INT4                 null,
   ID_ACTIVITY          INT4                 not null,
   constraint PK_GROUPS primary key (ID_GROUP)
);

/*==============================================================*/
/* Index: GROUPS_PK                                             */
/*==============================================================*/
create unique index GROUPS_PK on GROUPS (
ID_GROUP
);

/*==============================================================*/
/* Index: HASGROUPTYPE_FK                                       */
/*==============================================================*/
create  index HASGROUPTYPE_FK on GROUPS (
ID_GROUP_TYPE
);

/*==============================================================*/
/* Index: GROUPACTIVITY_FK                                      */
/*==============================================================*/
create  index GROUPACTIVITY_FK on GROUPS (
ID_ACTIVITY
);

/*==============================================================*/
/* Table: GROUPSTUDENT                                          */
/*==============================================================*/
create table GROUPSTUDENT (
   CIP                  CHAR(8)              not null,
   ID_GROUP             INT4                 not null,
   constraint PK_GROUPSTUDENT primary key (CIP, ID_GROUP)
);

/*==============================================================*/
/* Index: GROUPSTUDENT_PK                                       */
/*==============================================================*/
create unique index GROUPSTUDENT_PK on GROUPSTUDENT (
CIP,
ID_GROUP
);

/*==============================================================*/
/* Index: GROUPSTUDENT_FK                                       */
/*==============================================================*/
create  index GROUPSTUDENT_FK on GROUPSTUDENT (
CIP
);

/*==============================================================*/
/* Index: GROUPSTUDENT_FK2                                      */
/*==============================================================*/
create  index GROUPSTUDENT_FK2 on GROUPSTUDENT (
ID_GROUP
);

/*==============================================================*/
/* Table: GROUPTYPE                                             */
/*==============================================================*/
create table GROUPTYPE (
   ID_GROUP_TYPE        SERIAL               not null,
   TYPE                 TEXT                 not null,
   MIN_DEFAULT          INT4                 null,
   MAX_DEFAULT          INT4                 null,
   constraint PK_GROUPTYPE primary key (ID_GROUP_TYPE)
);

/*==============================================================*/
/* Index: GROUPTYPE_PK                                          */
/*==============================================================*/
create unique index GROUPTYPE_PK on GROUPTYPE (
ID_GROUP_TYPE
);

/*==============================================================*/
/* Table: LOGS                                                  */
/*==============================================================*/
create table LOGS (
   ID_LOG               SERIAL               not null,
   CIP                  CHAR(8)              null,
   DESCRIPTION          TEXT                 not null,
   LOG_TIMESTAMP        DATE                 not null,
   constraint PK_LOGS primary key (ID_LOG)
);

/*==============================================================*/
/* Index: LOGS_PK                                               */
/*==============================================================*/
create unique index LOGS_PK on LOGS (
ID_LOG
);

/*==============================================================*/
/* Index: HASLOG_FK                                             */
/*==============================================================*/
create  index HASLOG_FK on LOGS (
CIP
);

/*==============================================================*/
/* Table: MEMBERCLASS                                           */
/*==============================================================*/
create table MEMBERCLASS (
   CIP                  CHAR(8)              not null,
   ID_CLASS             TEXT                 not null,
   constraint PK_MEMBERCLASS primary key (CIP, ID_CLASS)
);

/*==============================================================*/
/* Index: MEMBERCLASS_PK                                        */
/*==============================================================*/
create unique index MEMBERCLASS_PK on MEMBERCLASS (
CIP,
ID_CLASS
);

/*==============================================================*/
/* Index: MEMBERCLASS_FK                                        */
/*==============================================================*/
create  index MEMBERCLASS_FK on MEMBERCLASS (
CIP
);

/*==============================================================*/
/* Index: MEMBERCLASS_FK2                                       */
/*==============================================================*/
create  index MEMBERCLASS_FK2 on MEMBERCLASS (
ID_CLASS
);

/*==============================================================*/
/* Table: MEMBERS                                               */
/*==============================================================*/
create table MEMBERS (
   CIP                  CHAR(8)              not null,
   ID_MEMBER_STATUS     INT4                 null,
   LAST_NAME            TEXT                 null,
   FIRST_NAME           TEXT                 null,
   EMAIL                TEXT                 null,
   constraint PK_MEMBERS primary key (CIP)
);

/*==============================================================*/
/* Index: MEMBERS_PK                                            */
/*==============================================================*/
create unique index MEMBERS_PK on MEMBERS (
CIP
);

/*==============================================================*/
/* Index: HASMEMBERSTATUS_FK                                    */
/*==============================================================*/
create  index HASMEMBERSTATUS_FK on MEMBERS (
ID_MEMBER_STATUS
);

/*==============================================================*/
/* Table: MEMBERSTATUS                                          */
/*==============================================================*/
create table MEMBERSTATUS (
   ID_MEMBER_STATUS     SERIAL               not null,
   STATUS               TEXT                 not null,
   constraint PK_MEMBERSTATUS primary key (ID_MEMBER_STATUS)
);

/*==============================================================*/
/* Index: MEMBERSTATUS_PK                                       */
/*==============================================================*/
create unique index MEMBERSTATUS_PK on MEMBERSTATUS (
ID_MEMBER_STATUS
);

/*==============================================================*/
/* Table: REQUEST                                               */
/*==============================================================*/
create table REQUEST (
   ID_ACTIVITY          INT4                 not null,
   CIP_SEEKING          CHAR(8)              not null,
   CIP_REQUESTED        CHAR(8)              not null,
   ID_REQUEST_TYPE      INT4                 not null,
   REQUEST_TIMESTAMP    DATE                 not null,
   constraint PK_REQUEST primary key (ID_ACTIVITY, CIP_SEEKING, CIP_REQUESTED, ID_REQUEST_TYPE)
);

/*==============================================================*/
/* Index: REQUEST_PK                                            */
/*==============================================================*/
create unique index REQUEST_PK on REQUEST (
ID_ACTIVITY,
CIP_SEEKING,
CIP_REQUESTED,
ID_REQUEST_TYPE
);

/*==============================================================*/
/* Index: REQUEST_FK                                            */
/*==============================================================*/
create  index REQUEST_FK on REQUEST (
ID_ACTIVITY
);

/*==============================================================*/
/* Index: REQUEST_FK2                                           */
/*==============================================================*/
create  index REQUEST_FK2 on REQUEST (
CIP_SEEKING
);

/*==============================================================*/
/* Index: REQUEST2_FK                                           */
/*==============================================================*/
create  index REQUEST2_FK on REQUEST (
CIP_REQUESTED
);

/*==============================================================*/
/* Index: REQUEST_FK3                                           */
/*==============================================================*/
create  index REQUEST_FK3 on REQUEST (
ID_REQUEST_TYPE
);

/*==============================================================*/
/* Table: REQUESTTYPE                                           */
/*==============================================================*/
create table REQUESTTYPE (
   ID_REQUEST_TYPE      SERIAL               not null,
   REQUEST_TYPE         TEXT                 not null,
   constraint PK_REQUESTTYPE primary key (ID_REQUEST_TYPE)
);

/*==============================================================*/
/* Index: REQUESTTYPE_PK                                        */
/*==============================================================*/
create unique index REQUESTTYPE_PK on REQUESTTYPE (
ID_REQUEST_TYPE
);

alter table ACTIVITIES
   add constraint FK_ACTIVITI_HASCLASS_CLASSES foreign key (ID_CLASS)
      references CLASSES (ID_CLASS)
      on delete restrict on update restrict;

alter table ACTIVITIES
   add constraint FK_ACTIVITI_USERACTIV_MEMBERS foreign key (CIP_IN_CHARGE)
      references MEMBERS (CIP)
      on delete restrict on update restrict;

alter table CLASSES
   add constraint FK_CLASSES_HASAP_AP foreign key (ID_AP)
      references AP (ID_AP)
      on delete restrict on update restrict;

alter table CLASSES
   add constraint FK_CLASSES_HASAPP_APP foreign key (ID_APP)
      references APP (ID_APP)
      on delete restrict on update restrict;

alter table FRIENDLIST
   add constraint FK_FRIENDLI_FRIENDLIS_MEMBERS foreign key (CIP)
      references MEMBERS (CIP)
      on delete restrict on update restrict;

alter table FRIENDLIST
   add constraint FK_FRIENDLI_FRIENDLIS_MEMBERS2 foreign key (FRIEND_CIP)
      references MEMBERS (CIP)
      on delete restrict on update restrict;

alter table GROUPS
   add constraint FK_GROUPS_GROUPACTI_ACTIVITI foreign key (ID_ACTIVITY)
      references ACTIVITIES (ID_ACTIVITY)
      on delete restrict on update restrict;

alter table GROUPS
   add constraint FK_GROUPS_HASGROUPT_GROUPTYP foreign key (ID_GROUP_TYPE)
      references GROUPTYPE (ID_GROUP_TYPE)
      on delete restrict on update restrict;

alter table GROUPSTUDENT
   add constraint FK_GROUPSTU_GROUPSTUD_GROUPS foreign key (ID_GROUP)
      references GROUPS (ID_GROUP)
      on delete restrict on update restrict;

alter table GROUPSTUDENT
   add constraint FK_GROUPSTU_GROUPSTUD_MEMBERS foreign key (CIP)
      references MEMBERS (CIP)
      on delete restrict on update restrict;

alter table LOGS
   add constraint FK_LOGS_HASLOG_MEMBERS foreign key (CIP)
      references MEMBERS (CIP)
      on delete restrict on update restrict;

alter table MEMBERCLASS
   add constraint FK_MEMBERCL_MEMBERCLA_CLASSES foreign key (ID_CLASS)
      references CLASSES (ID_CLASS)
      on delete restrict on update restrict;

alter table MEMBERCLASS
   add constraint FK_MEMBERCL_MEMBERCLA_MEMBERS foreign key (CIP)
      references MEMBERS (CIP)
      on delete restrict on update restrict;

alter table MEMBERS
   add constraint FK_MEMBERS_HASMEMBER_MEMBERST foreign key (ID_MEMBER_STATUS)
      references MEMBERSTATUS (ID_MEMBER_STATUS)
      on delete restrict on update restrict;

alter table REQUEST
   add constraint FK_REQUEST_REQUEST_ACTIVITI foreign key (ID_ACTIVITY)
      references ACTIVITIES (ID_ACTIVITY)
      on delete restrict on update restrict;

alter table REQUEST
   add constraint FK_REQUEST_REQUEST_MEMBERS foreign key (CIP_SEEKING)
      references MEMBERS (CIP)
      on delete restrict on update restrict;

alter table REQUEST
   add constraint FK_REQUEST_REQUEST_REQUESTT foreign key (ID_REQUEST_TYPE)
      references REQUESTTYPE (ID_REQUEST_TYPE)
      on delete restrict on update restrict;

alter table REQUEST
   add constraint FK_REQUEST_REQUEST2_MEMBERS foreign key (CIP_REQUESTED)
      references MEMBERS (CIP)
      on delete restrict on update restrict;
