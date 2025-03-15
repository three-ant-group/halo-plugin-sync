import { definePlugin } from "@halo-dev/console-shared";
import { IconPlug } from "@halo-dev/components";
import { markRaw } from "vue";
import BlogSyncView from "@/views/BlogSyncView.vue";

export default definePlugin({
  components: {},
  routes: [
    {
      parentName: "ToolsRoot",
      route: {
        //浏览器路径
        path: "/blog-sync",
        name: "blogSync",
        component: BlogSyncView,
        meta: {
          title: "博客同步",
          searchable: true,
          menu: {
            name: "博客同步",
            group: "tool",
            icon: markRaw(IconPlug),
            priority: 0,
          },
        },
      },
    },
  ],
  extensionPoints: {},
});
