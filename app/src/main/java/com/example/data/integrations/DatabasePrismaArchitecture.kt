package com.example.data.integrations

/**
 * Enterprise PostgreSQL & Prisma ORM Schema & Server-side API Architecture
 * Complete database models, relations, migrations and REST/GraphQL endpoint mapping
 * for the Nexus Ecosystem Platform.
 */
object DatabasePrismaArchitecture {

    const val PRISMA_SCHEMA_SPEC = """
// ==========================================
// Prisma 5+ Schema for Nexus Unified Platform
// Database: PostgreSQL 16+
// ==========================================

datasource db {
  provider = "postgresql"
  url      = env("DATABASE_URL")
}

generator client {
  provider = "prisma-client-js"
}

enum Role {
  MEMBER
  INSTRUCTOR
  ENTERPRISE
  ADMIN
}

enum Tier {
  FREE
  PRO
  ELITE
  LIFETIME
}

enum Priority {
  LOW
  MEDIUM
  HIGH
  URGENT
}

enum Status {
  OPEN
  IN_PROGRESS
  RESOLVED
  CANCELLED
}

enum PaymentStatus {
  INITIATED
  PAID
  FAILED
  REFUNDED
}

model User {
  id               String          @id @default(uuid())
  email            String          @unique
  phone            String?         @unique
  passwordHash     String?
  name             String
  role             Role            @default(MEMBER)
  memberId         String          @unique
  tier             Tier            @default(FREE)
  points           Int             @default(500)
  streakDays       Int             @default(1)
  isVerified       Boolean         @default(false)
  avatarUrl        String?
  createdAt        DateTime        @default(now())
  updatedAt        DateTime        @updatedAt

  // Relationships
  fitnessLogs      FitnessLog[]
  enrollments      CourseEnrollment[]
  communityPosts   CommunityPost[]
  postComments     PostComment[]
  jobApplications  JobApplication[]
  rewardRedemptions RewardRedemption[]
  supportTickets   SupportTicket[]
  payments         PaymentTransaction[]
  liveClassRosters LiveClassAttendee[]

  @@index([email, memberId])
}

model FitnessLog {
  id             String    @id @default(uuid())
  userId         String
  user           User      @relation(fields: [userId], references: [id], onDelete: Cascade)
  date           DateTime  @default(now())
  steps          Int       @default(0)
  targetSteps    Int       @default(10000)
  caloriesBurned Int       @default(0)
  waterMl        Int       @default(0)
  sleepHours     Float     @default(0)
  weightKg       Float?
  createdAt      DateTime  @default(now())

  @@index([userId, date])
}

model Course {
  id               String             @id @default(uuid())
  title            String
  slug             String             @unique
  description      String
  instructor       String
  category         String
  durationHours    String
  level            String
  rating           Float              @default(5.0)
  thumbnailUrl     String?
  isPublished      Boolean            @default(true)
  lessons          Lesson[]
  enrollments      CourseEnrollment[]
  createdAt        DateTime           @default(now())
}

model Lesson {
  id          String   @id @default(uuid())
  courseId    String
  course      Course   @relation(fields: [courseId], references: [id], onDelete: Cascade)
  title       String
  videoUrl    String
  durationMin Int
  orderIndex  Int
  summary     String?
}

model CourseEnrollment {
  id              String   @id @default(uuid())
  userId          String
  user            User     @relation(fields: [userId], references: [id], onDelete: Cascade)
  courseId        String
  course          Course   @relation(fields: [courseId], references: [id], onDelete: Cascade)
  progressPercent Float    @default(0)
  isCompleted     Boolean  @default(false)
  enrolledAt      DateTime @default(now())

  @@unique([userId, courseId])
}

model LiveClass {
  id            String              @id @default(uuid())
  title         String
  instructor    String
  category      String
  scheduledAt   DateTime
  durationMin   Int
  zoomMeetingId String
  zoomPasscode  String
  isLiveNow     Boolean             @default(false)
  attendees     LiveClassAttendee[]
}

model LiveClassAttendee {
  id          String    @id @default(uuid())
  liveClassId String
  liveClass   LiveClass @relation(fields: [liveClassId], references: [id], onDelete: Cascade)
  userId      String
  user        User      @relation(fields: [userId], references: [id], onDelete: Cascade)
  joinedAt    DateTime  @default(now())

  @@unique([liveClassId, userId])
}

model CommunityPost {
  id           String        @id @default(uuid())
  userId       String
  user         User          @relation(fields: [userId], references: [id], onDelete: Cascade)
  content      String
  tag          String
  likesCount   Int           @default(0)
  createdAt    DateTime      @default(now())
  comments     PostComment[]
}

model PostComment {
  id        String        @id @default(uuid())
  postId    String
  post      CommunityPost @relation(fields: [postId], references: [id], onDelete: Cascade)
  userId    String
  user      User          @relation(fields: [userId], references: [id], onDelete: Cascade)
  text      String
  createdAt DateTime      @default(now())
}

model Job {
  id           String           @id @default(uuid())
  title        String
  company      String
  location     String
  type         String
  salaryRange  String
  category     String
  description  String
  tags         String[]
  createdAt    DateTime         @default(now())
  applications JobApplication[]
}

model JobApplication {
  id          String   @id @default(uuid())
  jobId       String
  job         Job      @relation(fields: [jobId], references: [id], onDelete: Cascade)
  userId      String
  user        User     @relation(fields: [userId], references: [id], onDelete: Cascade)
  resumeUrl   String?
  coverLetter String?
  appliedAt   DateTime @default(now())

  @@unique([jobId, userId])
}

model Reward {
  id           String             @id @default(uuid())
  title        String
  pointsCost   Int
  category     String
  discountCode String
  description  String
  redemptions  RewardRedemption[]
}

model RewardRedemption {
  id         String   @id @default(uuid())
  rewardId   String
  reward     Reward   @relation(fields: [rewardId], references: [id])
  userId     String
  user       User     @relation(fields: [userId], references: [id], onDelete: Cascade)
  redeemedAt DateTime @default(now())
}

model SupportTicket {
  id           String          @id @default(uuid())
  ticketNumber String          @unique
  userId       String
  user         User            @relation(fields: [userId], references: [id], onDelete: Cascade)
  subject      String
  category     String
  priority     Priority        @default(MEDIUM)
  status       Status          @default(OPEN)
  createdAt    DateTime        @default(now())
  messages     TicketMessage[]
}

model TicketMessage {
  id        String        @id @default(uuid())
  ticketId  String
  ticket    SupportTicket @relation(fields: [ticketId], references: [id], onDelete: Cascade)
  senderId  String
  text      String
  isAdmin   Boolean       @default(false)
  createdAt DateTime      @default(now())
}

model PaymentTransaction {
  id             String        @id @default(uuid())
  userId         String
  user           User          @relation(fields: [userId], references: [id])
  gatewayOrderId String        @unique
  paymentId      String?       @unique
  amount         Float
  currency       String        @default("USD")
  planTier       Tier
  method         String        // UPI, CARD, NETBANKING
  status         PaymentStatus @default(INITIATED)
  invoiceUrl     String?
  createdAt      DateTime      @default(now())
}
"""

    const val SERVER_API_ROUTES_SUMMARY = """
// Nexus Backend Architecture API Endpoints:
// Auth:
//   POST /api/v1/auth/register
//   POST /api/v1/auth/send-otp
//   POST /api/v1/auth/verify-otp
//   POST /api/v1/auth/login
// User & Member ID:
//   GET  /api/v1/users/me
//   GET  /api/v1/users/member-card
// Health & Fitness:
//   GET  /api/v1/fitness/daily-summary
//   POST /api/v1/fitness/log-activity
// Courses & Live:
//   GET  /api/v1/courses
//   POST /api/v1/courses/:id/enroll
//   GET  /api/v1/live-classes/upcoming
//   POST /api/v1/live-classes/:id/join-token
// Payments & Subscriptions:
//   POST /api/v1/payments/create-order
//   POST /api/v1/payments/verify-signature
//   POST /api/v1/payments/webhook
// AI Assistant:
//   POST /api/v1/ai/chat-stream
// Admin:
//   GET  /api/v1/admin/metrics
//   GET  /api/v1/admin/users
//   PATCH /api/v1/admin/users/:id/role
"""
}
