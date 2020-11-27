<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'RuoYiGit',
  data() {
    return {
      url: 'https://github.com/FromSouthToNorth/blog-admin'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
