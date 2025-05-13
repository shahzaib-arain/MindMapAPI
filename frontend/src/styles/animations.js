// src/styles/animations.js
import { cubicBezier } from 'framer-motion';

// Basic animations
export const fadeIn = {
  initial: { opacity: 0 },
  animate: { opacity: 1 },
  exit: { opacity: 0 },
  transition: { duration: 0.3 }
};

export const slideUp = {
  initial: { y: 20, opacity: 0 },
  animate: { y: 0, opacity: 1 },
  exit: { y: -20, opacity: 0 },
  transition: { duration: 0.4, ease: cubicBezier(0.16, 1, 0.3, 1) }
};

export const slideInFromLeft = {
  initial: { x: -50, opacity: 0 },
  animate: { x: 0, opacity: 1 },
  transition: { duration: 0.5 }
};

export const scaleUp = {
  initial: { scale: 0.95, opacity: 0 },
  animate: { scale: 1, opacity: 1 },
  transition: { duration: 0.3 }
};

// Page transitions
export const pageVariants = {
  initial: {
    opacity: 0,
    x: '-5%',
    transition: { duration: 0.4, ease: cubicBezier(0.16, 1, 0.3, 1) }
  },
  in: {
    opacity: 1,
    x: 0,
    transition: { duration: 0.4, ease: cubicBezier(0.16, 1, 0.3, 1) }
  },
  out: {
    opacity: 0,
    x: '5%',
    transition: { duration: 0.4, ease: cubicBezier(0.16, 1, 0.3, 1) }
  }
};

// Component-specific animations
export const journalCardAnimation = {
  initial: { y: 20, opacity: 0 },
  animate: { y: 0, opacity: 1 },
  transition: { duration: 0.3 },
  whileHover: {
    y: -5,
    boxShadow: '0 10px 25px -5px rgba(108, 99, 255, 0.2)',
    transition: { duration: 0.2 }
  }
};

export const buttonAnimation = {
  whileHover: {
    scale: 1.02,
    boxShadow: '0 4px 12px rgba(108, 99, 255, 0.25)',
    transition: { duration: 0.2 }
  },
  whileTap: {
    scale: 0.98,
    transition: { duration: 0.1 }
  }
};

export const navItemAnimation = {
  whileHover: {
    backgroundColor: 'rgba(108, 99, 255, 0.08)',
    transition: { duration: 0.2 }
  },
  whileTap: {
    scale: 0.95,
    transition: { duration: 0.1 }
  }
};

// Stagger animations
export const staggerContainer = {
  initial: {},
  animate: {
    transition: {
      staggerChildren: 0.1,
      delayChildren: 0.2
    }
  }
};

// Special effects
export const floatingAnimation = {
  animate: {
    y: [0, -10, 0],
    transition: {
      duration: 4,
      repeat: Infinity,
      ease: 'easeInOut'
    }
  }
};

export const pulseAnimation = {
  animate: {
    scale: [1, 1.02, 1],
    transition: {
      duration: 2,
      repeat: Infinity,
      ease: 'easeInOut'
    }
  }
};

// Text animations
export const textFadeIn = {
  initial: { opacity: 0, y: 10 },
  animate: { opacity: 1, y: 0 },
  transition: { duration: 0.5 }
};

export const letterSpaceIn = {
  initial: { letterSpacing: '-0.5em', opacity: 0 },
  animate: { letterSpacing: '0em', opacity: 1 },
  transition: { duration: 0.5 }
};