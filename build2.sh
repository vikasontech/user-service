#!/bin/bash

set -e  # Exit on any error

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to print colored messages
print_step() {
    echo -e "${BLUE}==>${NC} ${GREEN}$1${NC}"
}

print_error() {
    echo -e "${RED}Error: $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}Warning: $1${NC}"
}

# Check if there are changes to commit
if [[ -z $(git status -s) ]]; then
    print_warning "No changes to commit. Skipping git operations."
    SKIP_GIT=true
else
    SKIP_GIT=false
fi

# Git operations
if [ "$SKIP_GIT" = false ]; then
    print_step "Git Status:"
    git status --short
    echo ""
    
    # Ask for commit message
    read -p "Enter commit message: " commit_message
    
    if [ -z "$commit_message" ]; then
        print_error "Commit message cannot be empty!"
        exit 1
    fi
    
    # Get current branch
    current_branch=$(git rev-parse --abbrev-ref HEAD)
    print_step "Current branch: $current_branch"
    
    # Stage all changes
    print_step "Staging changes..."
    git add .
    
    # Commit
    print_step "Committing changes..."
    git commit -m "$commit_message"
    
    # Ask for confirmation before push
    read -p "Push to remote branch '$current_branch'? (y/n): " confirm_push
    
    if [[ $confirm_push == "y" || $confirm_push == "Y" ]]; then
        print_step "Pushing to remote branch: $current_branch"
        git push origin "$current_branch"
        echo ""
    else
        print_warning "Push skipped. Changes committed locally only."
        echo ""
    fi
fi

# Maven compilation
print_step "Compilation..."
mvn clean install -DskipTests

# Docker build
print_step "Creating Docker image..."
docker build -t vikason/user-service:latest .

# Docker push
print_step "Pushing to Docker Hub..."
docker push vikason/user-service:latest

# Kubernetes deployment
print_step "Deploying to Kubernetes..."
kubectl rollout restart deployment/user-service -n saas-control

# Check deployment status
print_step "Checking deployment status..."
kubectl rollout status deployment/user-service -n saas-control

print_step "Finished successfully! ✓"